package com.kafkaexample.dto.validator;

import com.kafkaexample.dto.VehiclePositionCoordinateDTO;
import org.springframework.stereotype.Component;

@Component
public class VehiclePositionCoordinateValidator {

    public VehiclePositionCoordinateValidator() {}

    public void validate(VehiclePositionCoordinateDTO dto) throws IllegalArgumentException {
        // validate here and throws according exceptions
    }
}
