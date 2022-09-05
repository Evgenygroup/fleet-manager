package com.iomoto.fleetmanager.repository;

import com.iomoto.fleetmanager.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepositiory extends JpaRepository<VehicleEntity,Long> {
}
