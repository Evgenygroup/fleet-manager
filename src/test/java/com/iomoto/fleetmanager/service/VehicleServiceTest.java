package com.iomoto.fleetmanager.service;

import com.iomoto.fleetmanager.entity.VehicleEntity;
import com.iomoto.fleetmanager.exception.VehicleNotFoundException;
import com.iomoto.fleetmanager.repository.VehicleRepositiory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepositiory vehicleRepositiory;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    public void testCreateVehicle() {

        VehicleEntity newVehicle = new VehicleEntity(
                null,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");

        VehicleEntity savedVehicle = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");

        when(vehicleRepositiory.save(newVehicle)).thenReturn(savedVehicle);

        VehicleEntity returnedVehicle = vehicleService.createVehicle(newVehicle);

        assertEquals(savedVehicle.getId(), returnedVehicle.getId());
        assertEquals(newVehicle.getName(), returnedVehicle.getName());
        assertEquals(newVehicle.getVin(), returnedVehicle.getVin());
        assertEquals(newVehicle.getLicensePlateNumber(), returnedVehicle.getLicensePlateNumber());
        assertEquals(newVehicle.getProperties(), returnedVehicle.getProperties());

        verify(vehicleRepositiory, times(1)).save(newVehicle);

    }


    @Test
    public void testGetAllVehicles_empty() {

        List<VehicleEntity> listOfSavedVehicles = Arrays.asList();

        when(vehicleRepositiory.findAll()).thenReturn(listOfSavedVehicles);

        List<VehicleEntity> vehiclesListResult = vehicleService.getAllVehicles();

        assertArrayEquals(vehiclesListResult.toArray(), listOfSavedVehicles.toArray());
        verify(vehicleRepositiory, times(1)).findAll();
    }


    @Test
    public void testGetAllVehicles() {
        VehicleEntity vehicle1 = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");
        VehicleEntity vehicle2 = new VehicleEntity(2L,
                "Volvo",
                "ERT5675563484w4",
                "WETR1253",
                "number of doors:2, color:red");
        VehicleEntity vehicle3 = new VehicleEntity(3L,
                "Renault",
                "RRT567567484w4",
                "TER123",
                "colour:green");

        List<VehicleEntity> listOfSavedVehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);

        when(vehicleRepositiory.findAll()).thenReturn(listOfSavedVehicles);

        List<VehicleEntity> vehicleListResult = vehicleService.getAllVehicles();

        assertArrayEquals(vehicleListResult.toArray(), listOfSavedVehicles.toArray());
        verify(vehicleRepositiory, times(1)).findAll();
    }

    @Test
    public void testGetVehicleById_success() {
        VehicleEntity savedVehicle = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");

        when(vehicleRepositiory.findById(savedVehicle.getId())).thenReturn(Optional.of(savedVehicle));
        VehicleEntity vehicleFound = vehicleService.getVehicleById(savedVehicle.getId());

        assertEquals(savedVehicle.getId(), vehicleFound.getId());
        assertEquals(savedVehicle.getName(), vehicleFound.getName());
        assertEquals(savedVehicle.getVin(), vehicleFound.getVin());
        assertEquals(savedVehicle.getLicensePlateNumber(), vehicleFound.getLicensePlateNumber());
        assertEquals(savedVehicle.getProperties(), vehicleFound.getProperties());


    }

    @Test
    public void testGetVehicleById_not_found() {
        Long wrongId = 2345L;
        Exception exception = assertThrows(VehicleNotFoundException.class, () ->
                vehicleService.getVehicleById(wrongId));

        verify(vehicleRepositiory, times(1)).findById(any());
        assertEquals("Vehicle not found", exception.getMessage());

    }

    @Test
    public void testUpdateVehicle() {
        VehicleEntity newVehicle = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");

        VehicleEntity oldVehicle = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER666",
                "colour:red, number of doors:2");


        when(vehicleRepositiory.findById(newVehicle.getId())).thenReturn(Optional.of(oldVehicle));
        when(vehicleRepositiory.save(newVehicle)).thenReturn(newVehicle);
        VehicleEntity vehicleActual = vehicleService.updateVehicle(1L, newVehicle);

        assertEquals(newVehicle.getId(), vehicleActual.getId());
        assertEquals(newVehicle.getName(), vehicleActual.getName());
        assertEquals(newVehicle.getVin(), vehicleActual.getVin());
        assertEquals(newVehicle.getLicensePlateNumber(), vehicleActual.getLicensePlateNumber());
        assertEquals(newVehicle.getProperties(), vehicleActual.getProperties());


        verify(vehicleRepositiory, times(1)).save(any());
        verify(vehicleRepositiory, times(1)).findById(any());
    }

    @Test
    public void testUpdateVehicle_notFound() {

        Long wrongId = 2345L;
        VehicleEntity newVehicle = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");

        Exception exception = assertThrows(VehicleNotFoundException.class, () ->
                vehicleService.updateVehicle(wrongId, newVehicle));

        verify(vehicleRepositiory, times(1)).findById(wrongId);
        verify(vehicleRepositiory, times(0)).save(any());
        assertEquals("Vehicle not found", exception.getMessage());

    }

    @Test
    public void testDeleteVehicle_success() {
        VehicleEntity vehicleToDelete = new VehicleEntity(
                1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "number of doors:4");
        when(vehicleRepositiory.findById(vehicleToDelete.getId()))
                .thenReturn(Optional.of(vehicleToDelete));

        vehicleService.deleteVehicleById(vehicleToDelete.getId());
        verify(vehicleRepositiory, times(1))
                .deleteById(vehicleToDelete.getId());
    }

    @Test
    public void testDeleteVehicle_notFound() {

        Long wrongId = 2345L;
        Exception exception = assertThrows(VehicleNotFoundException.class, () ->
                vehicleService.deleteVehicleById(wrongId));

        verify(vehicleRepositiory, times(0)).deleteById(wrongId);
        assertEquals("Vehicle not found", exception.getMessage());


    }


}
