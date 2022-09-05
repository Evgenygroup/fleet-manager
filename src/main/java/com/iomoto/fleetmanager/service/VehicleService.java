package com.iomoto.fleetmanager.service;


import com.iomoto.fleetmanager.entity.VehicleEntity;

import java.util.List;

public interface VehicleService {

    List<VehicleEntity> getAllVehicles();

    VehicleEntity getVehicleById(long id);

    VehicleEntity createVehicle(VehicleEntity vehicle);

    VehicleEntity updateVehicle(long id, VehicleEntity vehicle);

    void deleteVehicleById(long id);


}
