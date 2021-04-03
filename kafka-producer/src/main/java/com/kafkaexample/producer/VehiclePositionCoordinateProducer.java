package com.kafkaexample.producer;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehiclePositionCoordinateProducer {
    private final KafkaProducer producer;

    @Autowired
    public VehiclePositionCoordinateProducer(KafkaProducer kafkaProducer) {
        this.producer = kafkaProducer;
    }

    public void send(VehiclePositionCoordinate vehiclePositionCoordinate) {
        producer.sendMessage(vehiclePositionCoordinate);
    }

}
