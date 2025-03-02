package com.testing.application.TestingApplication.controllers;

import com.testing.application.TestingApplication.dto.ResponseWrapper;
import com.testing.application.TestingApplication.models.ClinicDetails;
import com.testing.application.TestingApplication.services.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseWrapper<List<ClinicDetails>>> getAllClinics() {
        return ResponseEntity.ok(new ResponseWrapper<>(null, clinicService.getAllClinics()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ClinicDetails>> getClinicById(@PathVariable String id) {
        try {
            ClinicDetails clinicDetails = clinicService.getClinicById(id);
            return ResponseEntity.ok(new ResponseWrapper<>(null, clinicDetails));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<ClinicDetails>> createClinic(@RequestBody ClinicDetails clinicDetails) {
        ClinicDetails clinic = clinicService.createClinicCentre(clinicDetails);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>("Successfully added clinic details", clinic));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper<ClinicDetails>> updateClinicDetails(@RequestBody ClinicDetails clinicDetails) {
        try {
            ClinicDetails updatedClinicDetails = clinicService.updateClinicDetails(clinicDetails);
            return ResponseEntity.ok(new ResponseWrapper<>("Updated Successfully", updatedClinicDetails));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteClinic(@PathVariable String id) {
        try {
            clinicService.deleteClinicById(id);
            return ResponseEntity.ok(new ResponseWrapper<>("Deleted Successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).
                    body(new ResponseWrapper<>(e.getMessage(), null));
        }
    }
}
