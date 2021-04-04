package com.kafkaexample.dto;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import com.kafkaexample.converter.NumberConverter;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@EqualsAndHashCode
public class VehiclePositionCoordinateDTO {
    private Integer x_coordinate;
    private Integer y_coordinate;
    private String vehicle_description;
    private Double vehicle_price;

    public VehiclePositionCoordinate toVehiclePositionCoordinate() {
        return VehiclePositionCoordinate.newBuilder()
                .setVehicleUuid(UUID.randomUUID().toString())
                .setXCoordinate(this.getX_coordinate())
                .setYCoordinate(this.getY_coordinate())
                .setVehicleDescription(this.getVehicle_description())
                .setVehiclePrice(NumberConverter.instance().doubleToInt(this.getVehicle_price()))
                .build();
    }
}
