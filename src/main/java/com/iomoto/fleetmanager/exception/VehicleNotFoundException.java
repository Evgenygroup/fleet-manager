package com.iomoto.fleetmanager.exception;

import lombok.Getter;

public class VehicleNotFoundException extends RuntimeException {

        @Getter
        private final Long id;

        public VehicleNotFoundException(Long id) {
            super("Vehicle not found");
            this.id = id;
        }
    }


