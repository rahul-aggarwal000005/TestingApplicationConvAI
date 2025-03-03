package com.testing.application.TestingApplication.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class ClinicDetails {

    @Id
    private String id;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private String contactNumber;
    private String openingHours;
    private double rating;

    @GeoSpatialIndexed
    private GeoJsonPoint location;  // GeoJSON point (longitude, latitude)
}
