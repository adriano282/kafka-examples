package com.kafkaexample.test_infrastructure.consumer;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class KafkaConsumerHelper {

    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerHelper.class.toString());

    private CountDownLatch latch = new CountDownLatch(1);
    private VehiclePositionCoordinate payload = null;

    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(VehiclePositionCoordinate vehiclePositionCoordinate) {
        LOGGER.info(String.format("kafka consumer test receiver payload='%s'", vehiclePositionCoordinate));
        payload = vehiclePositionCoordinate;
        latch.countDown();
    }

    public boolean isMessageReceived() {
        return latch.getCount() == 0;
    }

    public void awaitMessageForTenSeconds() throws Exception {
        latch.await(10000, TimeUnit.MILLISECONDS);
    }

    public VehiclePositionCoordinate getReceivedMessage() {
        return payload;
    }
}
