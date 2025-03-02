package com.testing.application.TestingApplication.services;

import com.testing.application.TestingApplication.models.ClinicDetails;
import com.testing.application.TestingApplication.repositories.ClinicRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<ClinicDetails> getAllClinics() {
        return clinicRepository.findAll();
    }

    public ClinicDetails createClinicCentre(ClinicDetails clinicDetails) {
         return clinicRepository.insert(clinicDetails);
    }

    // Merging Only Non-Null Fields Using Reflection
    private void mergeNonNullFields(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null && !(value instanceof Number && (Double) value == 0.0)) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error merging fields", e);
            }
        }
    }

    public String updateClinicDetails(ClinicDetails clinicDetails) {
        Optional<ClinicDetails> dbClinic = clinicRepository.findById(clinicDetails.getId());
        if(dbClinic.isEmpty()) {
            return "No clinic is associated with this id";
        }

        ClinicDetails existingClinic = dbClinic.get();
        mergeNonNullFields(clinicDetails, existingClinic);
        clinicRepository.save(existingClinic);
        
        return "Updated Successfully";
    }

    public String deleteClinicById(String id) {
        Optional<ClinicDetails> dbClinic = clinicRepository.findById(id);
        if(dbClinic.isEmpty()) {
            return "No clinic is associated with this id";
        }
        clinicRepository.deleteById(id);
        return "Deleted Successfully";
    }

    public ClinicDetails getClinicById(String id) {
        Optional<ClinicDetails> dbClinic = clinicRepository.findById(id);
        return dbClinic.orElse(null);
    }
}
