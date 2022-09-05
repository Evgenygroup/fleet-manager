package com.iomoto.fleetmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleErrorDto {

    private Long id;
    private String message;
    private LocalDateTime date = LocalDateTime.now();

    public VehicleErrorDto(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
