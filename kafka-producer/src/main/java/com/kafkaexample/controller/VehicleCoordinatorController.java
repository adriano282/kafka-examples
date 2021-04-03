package com.kafkaexample.controller;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import com.kafkaexample.dto.VehiclePositionCoordinateDTO;
import com.kafkaexample.dto.validator.VehiclePositionCoordinateValidator;
import com.kafkaexample.producer.VehiclePositionCoordinateProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vehicle-coordinate")
public class VehicleCoordinatorController {

    private VehiclePositionCoordinateProducer vehiclePositionCoordinateProducer;

    private VehiclePositionCoordinateValidator vehiclePositionCoordinateValidator;

    @Autowired
    VehicleCoordinatorController(VehiclePositionCoordinateProducer vehiclePositionCoordinateProducer,
                                 VehiclePositionCoordinateValidator vehiclePositionCoordinateValidator) {
        this.vehiclePositionCoordinateProducer = vehiclePositionCoordinateProducer;
        this.vehiclePositionCoordinateValidator = vehiclePositionCoordinateValidator;
    }

    @PostMapping(value = "/publish")
    public ResponseEntity sendMessageToKafkaTopic(
            @RequestBody VehiclePositionCoordinateDTO vehiclePositionCoordinateBody) {

        try {
            vehiclePositionCoordinateValidator.validate(vehiclePositionCoordinateBody);

            VehiclePositionCoordinate message =
                    vehiclePositionCoordinateBody.toVehiclePositionCoordinate();

            vehiclePositionCoordinateProducer.send(message);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

