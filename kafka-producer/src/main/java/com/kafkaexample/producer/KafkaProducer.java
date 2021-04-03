package com.kafkaexample.producer;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Profile(value = {"!test"})
public class KafkaProducer {

    private final Logger LOGGER = Logger.getLogger(KafkaProducer.class.toString());

    @Value("${topic.name}")
    private String topic;

    private final KafkaTemplate<String, VehiclePositionCoordinate> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, VehiclePositionCoordinate> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(VehiclePositionCoordinate vehiclePositionCoordinate) {
        LOGGER.log(Level.INFO, String.format("method=sendMessage, parameters=%s", vehiclePositionCoordinate));

        this.kafkaTemplate.send(this.topic, vehiclePositionCoordinate);

        LOGGER.log(Level.INFO, String.format("Produced vehicle position coordiante -> %s", vehiclePositionCoordinate));
    }
}
