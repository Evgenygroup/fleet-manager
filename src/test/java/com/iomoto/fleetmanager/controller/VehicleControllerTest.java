package com.iomoto.fleetmanager.controller;

import com.iomoto.fleetmanager.entity.VehicleEntity;
import com.iomoto.fleetmanager.exception.VehicleNotFoundException;
import com.iomoto.fleetmanager.service.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VehicleServiceImpl service;

    private VehicleEntity savedVehicle;

    @BeforeEach
    public void init() {
        savedVehicle = mockVehicleEntity();
    }

    @Test
    public void testGetAllVehicles_success() throws Exception {

        when(service.getAllVehicles())
                .thenReturn(createListOfEntities());

        mvc.perform(get("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(savedVehicle.getId()))
                .andExpect(jsonPath("$[0].name").value(savedVehicle.getName()))
                .andExpect(jsonPath("$[0].vin").value(savedVehicle.getVin()))
                .andExpect(jsonPath("$[0].licensePlateNumber").value(savedVehicle.getLicensePlateNumber()))
                .andExpect(jsonPath("$[0].properties.colour").value("red"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Volvo"))
                .andExpect(jsonPath("$[1].vin").value("ERT5675563484w4"))
                .andExpect(jsonPath("$[1].licensePlateNumber").value("WETR1253"))
                .andExpect(jsonPath("$[1].properties.colour").value("grey"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Renault"))
                .andExpect(jsonPath("$[2].vin").value("RRT567567484w4"))
                .andExpect(jsonPath("$[2].licensePlateNumber").value("TER123"))
                .andExpect(jsonPath("$[2].properties.colour").value("green"));

        verify(service, times(1)).getAllVehicles();
    }


    @Test
    public void testGetVehicleById_success() throws Exception {
        when(service.getVehicleById(savedVehicle.getId()))
                .thenReturn(savedVehicle);

        mvc.perform(get("/api/vehicles/{id}", savedVehicle.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(savedVehicle.getId()))
                .andExpect(jsonPath("$.name").value(savedVehicle.getName()))
                .andExpect(jsonPath("$.vin").value(savedVehicle.getVin()))
                .andExpect(jsonPath("$.licensePlateNumber").value(savedVehicle.getLicensePlateNumber()))
                .andExpect(jsonPath("$.properties.colour").value("red"));

        verify(service, times(1)).getVehicleById(savedVehicle.getId());

    }

    @Test
    public void testGetVehicleById_not_found() throws Exception {
        Long fakeId = 123456L;
        when(service.getVehicleById(fakeId))
                .thenThrow(new VehicleNotFoundException(fakeId));

        mvc.perform(get("/api/vehicles/{id}", fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    //    @Test
    public void testCreateNewVehicle_success() throws Exception {
        VehicleEntity newVehicle = new VehicleEntity(null,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "colour:red");

        when(service.createVehicle(newVehicle)).thenReturn(savedVehicle);

        mvc.perform(post("/api/vehicles")
                .content("{\"name\": \"Mercedes\"}")
                .content("{\"vin\": \"ERT567567484w4\"}")
                .content("{\"licensePlateNumber\": \"WER123\"}")
                .content("{\"properties\":\"{\"colour\":\"red\"}\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Mercedes"));

        verify(service, times(1)).createVehicle(newVehicle);
    }

    @Test
    public void removeVehicleByIdSuccess() throws Exception {
        Long id = 123L;
        mvc.perform(delete("/api/vehicles/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        verify(service, times(1)).deleteVehicleById(id);
    }

    @Test
    public void removeVehicleByIdNotFound() throws Exception {
        Long fakeId = 123L;
        doThrow(new VehicleNotFoundException(fakeId)).when(service).deleteVehicleById(fakeId);
        mvc.perform(delete("/api/vehicles/{id}", fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(service, times(1)).deleteVehicleById(fakeId);
    }


    private List<VehicleEntity> createListOfEntities() {
        VehicleEntity vehicle1 = savedVehicle;
        VehicleEntity vehicle2 = new VehicleEntity(2L,
                "Volvo",
                "ERT5675563484w4",
                "WETR1253",
                "{\"colour\":\"grey\"}");
        VehicleEntity vehicle3 = new VehicleEntity(3L,
                "Renault",
                "RRT567567484w4",
                "TER123",
                "{\"colour\":\"green\"}");

        return Arrays.asList(vehicle1, vehicle2, vehicle3);
    }


    private VehicleEntity mockVehicleEntity() {
        return new VehicleEntity(1L,
                "Mercedes",
                "ERT567567484w4",
                "WER123",
                "{\"colour\":\"red\"}");


    }


}
