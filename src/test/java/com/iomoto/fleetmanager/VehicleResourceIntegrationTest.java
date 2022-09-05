package com.iomoto.fleetmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleResourceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateNewVehicle() throws Exception {

        mvc.perform(post("/api/vehicles")
                .content("{\"name\": \"Mercedes\"}")
                .content("{\"vin\": \"ERT567567484w4\"}")
                .content("{\"licensePlateNumber\": \"WER123\"}")
                //   .content("{\"properties\": \"colour\":\"red\"}")
                .content("{\"properties\":\"{\"colour\":\"red\"}\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void testGetVehicles() throws Exception {

        mvc.perform(get("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists());
    }


}
