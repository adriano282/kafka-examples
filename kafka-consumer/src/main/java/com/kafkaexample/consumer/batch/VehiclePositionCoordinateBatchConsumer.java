package com.kafkaexample.consumer.batch;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class VehiclePositionCoordinateBatchConsumer {

    private static final Logger LOGGER = Logger.getLogger(VehiclePositionCoordinateBatchConsumer.class.toString());

    @KafkaListener(topics = "${topic.name}", groupId = "batch-consumer",
            containerFactory = "kafkaListenerContainerFactoryForBatchConsumer")
    public void consume(
            List<VehiclePositionCoordinate> vehiclePositionCoordinate,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
        LOGGER.info(String.format("[batch-consume] listSize: %s", vehiclePositionCoordinate.size()));
        acknowledgment.acknowledge();
    }
}
