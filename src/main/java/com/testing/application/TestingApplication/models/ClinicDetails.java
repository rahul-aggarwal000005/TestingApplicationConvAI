package com.testing.application.TestingApplication.models;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDetails {

    private String id;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private String contactNumber;
    private String openingHours;
    private double rating;

}
