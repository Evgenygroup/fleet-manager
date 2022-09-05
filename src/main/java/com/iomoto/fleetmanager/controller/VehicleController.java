package com.iomoto.fleetmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.iomoto.fleetmanager.dto.VehicleDto;
import com.iomoto.fleetmanager.entity.VehicleEntity;
import com.iomoto.fleetmanager.service.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleServiceImpl service;

    @Autowired
    public VehicleController(VehicleServiceImpl service) {
        this.service = service;
    }


    @GetMapping
    public List<VehicleDto> getAllVehicles() {

        return service.getAllVehicles().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable(name = "id") Long id) {
        VehicleEntity vehicle = service.getVehicleById(id);

        return ResponseEntity.ok().body(convertToDto(vehicle));
    }


    @PostMapping
    public ResponseEntity<VehicleDto> createNewVehicle(@RequestBody VehicleDto vehicleDto) {

        VehicleEntity vehicle = convertToEntity(vehicleDto);
        VehicleEntity vehicleCreated = service.createVehicle(vehicle);

        return new ResponseEntity<>(convertToDto(vehicleCreated), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable long id, @RequestBody VehicleDto vehicleDto) {

        VehicleEntity vehicle = service.updateVehicle(id, convertToEntity(vehicleDto));

        return ResponseEntity.ok().body(convertToDto(vehicle));
    }


    @DeleteMapping("{id}")
    public void removeVehicle(@PathVariable(name = "id") Long id) {

        service.deleteVehicleById(id);


    }


    private VehicleDto convertToDto(VehicleEntity vehicleEntity) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicleEntity.getId());
        vehicleDto.setName(vehicleEntity.getName());
        vehicleDto.setVin(vehicleEntity.getVin());
        vehicleDto.setLicensePlateNumber(vehicleEntity.getLicensePlateNumber());
        vehicleDto.setProperties(convertJsonStringToJsonNode(vehicleEntity.getProperties()));
        return vehicleDto;
    }

    private VehicleEntity convertToEntity(VehicleDto vehicleDto) {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setName(vehicleDto.getName());
        vehicleEntity.setVin(vehicleDto.getVin());
        vehicleEntity.setLicensePlateNumber(vehicleDto.getLicensePlateNumber());
        vehicleEntity.setProperties(vehicleDto.getProperties().toString());
        return vehicleEntity;
    }


    private JsonNode convertJsonStringToJsonNode(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = JsonNodeFactory.instance.objectNode();

        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            //   System.out.println(e.getCause());
        }

        return jsonNode;


    }


}
