package com.iomoto.fleetmanager.service;


import com.iomoto.fleetmanager.entity.VehicleEntity;
import com.iomoto.fleetmanager.exception.VehicleNotFoundException;
import com.iomoto.fleetmanager.repository.VehicleRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepositiory vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepositiory vehicleRepositiory) {

        this.vehicleRepository = vehicleRepositiory;
    }


    @Override
    public List<VehicleEntity> getAllVehicles() {

        return vehicleRepository.findAll();
    }


    @Override
    public VehicleEntity getVehicleById(long id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));

    }


    @Override
    public VehicleEntity createVehicle(VehicleEntity vehicle) {

        return vehicleRepository.save(vehicle);
    }


    @Override
    public VehicleEntity updateVehicle(long id, VehicleEntity vehicleUpdate) {

        VehicleEntity vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));

        vehicle.setName(vehicleUpdate.getName());
        vehicle.setVin(vehicleUpdate.getVin());
        vehicle.setLicensePlateNumber(vehicleUpdate.getLicensePlateNumber());
        vehicle.setProperties(vehicleUpdate.getProperties());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicleById(long id) {
        getVehicleById(id);
        vehicleRepository.deleteById(id);
    }


}

