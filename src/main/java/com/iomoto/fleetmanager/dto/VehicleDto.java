package com.iomoto.fleetmanager.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class VehicleDto {


    private Long id;
    private String name;
    private String vin;
    private String licensePlateNumber;
    private JsonNode properties;

}

