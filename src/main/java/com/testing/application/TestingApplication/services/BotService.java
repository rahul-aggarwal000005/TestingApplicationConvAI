package com.testing.application.TestingApplication.services;

import com.testing.application.TestingApplication.models.ClinicDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BotService {
    List<ClinicDetails> clinicDetails = Arrays.asList(
            ClinicDetails.builder()
                    .id("1")
                    .name("Healthy Life Clinic")
                    .address("D-202 Budh Nagar Inderpuri")
                    .city("New Delhi")
                    .zipCode("110012")
                    .contactNumber("+91-9818576300")
                    .openingHours("9 AM - 6 PM")
                    .rating(4.5)
                    .build(),

            ClinicDetails.builder()
                    .id("2")
                    .name("Wellness Care Center")
                    .address("RA-53 Main Inderpuri")
                    .city("New Delhi")
                    .zipCode("110012")
                    .contactNumber("+91-9773838560")
                    .openingHours("8 AM - 5 PM")
                    .rating(4.2)
                    .build()
    );

    public List<ClinicDetails> getAllClinics() {
        return clinicDetails;
    }
}
