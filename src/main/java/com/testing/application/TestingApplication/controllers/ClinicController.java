package com.testing.application.TestingApplication.controllers;

import com.testing.application.TestingApplication.models.ClinicDetails;
import com.testing.application.TestingApplication.services.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ClinicDetails>> getAllClinics() {
        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    @GetMapping("/getAllClinics/{id}")
    public ResponseEntity<?> getClinicById(@PathVariable String id) {
        try {
            ClinicDetails clinicDetails = clinicService.getClinicById(id);
            return ResponseEntity.ok(clinicDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/createClinic")
    public ResponseEntity<ClinicDetails> createClinic(@RequestBody ClinicDetails clinicDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicService.createClinicCentre(clinicDetails));
    }

    @PutMapping("/updateClinic")
    public ResponseEntity<?> updateClinicDetails(@RequestBody ClinicDetails clinicDetails) {
        try {
            ClinicDetails updatedClinicDetails = clinicService.updateClinicDetails(clinicDetails);
            return ResponseEntity.ok(updatedClinicDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteClinic/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable String id) {
        try {
            clinicService.deleteClinicById(id);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
