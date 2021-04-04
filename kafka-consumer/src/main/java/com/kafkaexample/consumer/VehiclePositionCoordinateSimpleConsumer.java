package com.kafkaexample.consumer;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Reference tor the implementation using a RETRY and ERROR topic:
 * https://medium.com/trendyol-tech/how-to-implement-retry-logic-with-spring-kafka-710b51501ce2
 */
@Component
public class VehiclePositionCoordinateSimpleConsumer {

    private static final Logger LOGGER = Logger.getLogger(VehiclePositionCoordinateSimpleConsumer.class.toString());

    private static final String RETRY = "_RETRY";

    @Autowired
    private KafkaTemplate<String, VehiclePositionCoordinate> kafkaTemplate;

    @KafkaListener(topics = "${topic.name}", groupId = "main-consumer")
    public void consume(
            VehiclePositionCoordinate vehiclePositionCoordinate,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        LOGGER.info(String.format("[main-consume] message received: %s", vehiclePositionCoordinate));

        try {
            // Consume logic goes here

            // Simulates an error
            if (vehiclePositionCoordinate.getXCoordinate() == 10)
                throw new IllegalArgumentException();

        } catch (Exception e) {
            LOGGER.warning("An exception %s happened while trying to consume %s message.");
            kafkaTemplate.send(topic + RETRY, vehiclePositionCoordinate);
            LOGGER.warning(String.format("Message %s sent to %s topic for retry so the main consumer will be not blocked."
                    ,vehiclePositionCoordinate.toString(), topic + RETRY));
            return;
        }

        LOGGER.info(String.format("[main-consume] message consumed: %s", vehiclePositionCoordinate));
    }

    @KafkaListener(topics = "${topic.name}", containerFactory = "kafkaListenerContainerFactoryWithDeadLetterPublishingRecoverer",
            groupId = "with-retry-on-main-topic")
    public void consumeWithRetry(VehiclePositionCoordinate vehiclePositionCoordinate) {
        LOGGER.info(String.format("[with-retry-on-main-topic] message received: %s", vehiclePositionCoordinate));

        // Simulates an error
        //     if (vehiclePositionCoordinate.getXCoordinate() == 10)
        //        throw new IllegalArgumentException();

        LOGGER.info(String.format("[with-retry-on-main-topic] message consumed: %s", vehiclePositionCoordinate));
    }

    @KafkaListener(topics = "${topic.name}" + RETRY, containerFactory = "kafkaListenerContainerFactoryForRetryTopic", groupId = "with-retry-on-retry-topic")
    public void listenForRetryTopic(VehiclePositionCoordinate vehiclePositionCoordinate) {
        LOGGER.info(String.format("[with-retry-on-retry-topic] message received with retry: %s", vehiclePositionCoordinate));

        // Simulates an error
        if (vehiclePositionCoordinate.getXCoordinate() == 10)
            throw new IllegalArgumentException();

        LOGGER.info(String.format("[with-retry-on-retry-topic] message consumed with retry: %s", vehiclePositionCoordinate));
    }

}
