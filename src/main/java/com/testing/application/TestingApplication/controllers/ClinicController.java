package com.testing.application.TestingApplication.controllers;

import com.testing.application.TestingApplication.models.ClinicDetails;
import com.testing.application.TestingApplication.services.ClinicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/getAllClinics")
    public List<ClinicDetails> getAllClinics() {
        return clinicService.getAllClinics();
    }

    @GetMapping("/getAllClinics/{id}")
    public ClinicDetails getClinicById(@PathVariable String id) {
        return clinicService.getClinicById(id);
    }

    @PostMapping("/createClinic")
    public ClinicDetails createClinic(@RequestBody ClinicDetails clinicDetails) {
        return clinicService.createClinicCentre(clinicDetails);
    }

    @PutMapping("/updateClinic")
    public String updateClinicDetails(@RequestBody ClinicDetails clinicDetails) {
        return clinicService.updateClinicDetails(clinicDetails);
    }

    @DeleteMapping("/deleteClinic/{id}")
    public String deleteClinic(@PathVariable String id) {
        return clinicService.deleteClinicById(id);
    }
}
