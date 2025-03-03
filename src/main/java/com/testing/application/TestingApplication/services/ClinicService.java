package com.testing.application.TestingApplication.services;

import com.testing.application.TestingApplication.dto.GeoLocation;
import com.testing.application.TestingApplication.models.ClinicDetails;
import com.testing.application.TestingApplication.repositories.ClinicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private static final Logger logger = LoggerFactory.getLogger(ClinicService.class);

    private final ClinicRepository clinicRepository;
    private final GeocodingService geocodingService;
    private final MongoTemplate mongoTemplate;

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

    public ClinicService(ClinicRepository clinicRepository, GeocodingService geocodingService, MongoTemplate mongoTemplate) {
        this.clinicRepository = clinicRepository;
        this.geocodingService = geocodingService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<ClinicDetails> getAllClinics() {
        logger.info("Fetching all clinics details");
        return clinicRepository.findAll();
    }

    public ClinicDetails createClinicCentre(ClinicDetails clinicDetails) {
        GeoLocation geoLocation = geocodingService.getLatLongForLocation(clinicDetails.getCity(), clinicDetails.getZipCode());
        clinicDetails.setLocation(new GeoJsonPoint(geoLocation.longitude(), geoLocation.latitude()));

        logger.info("Creating new clinic centre");
        return clinicRepository.insert(clinicDetails);
    }

    public ClinicDetails getClinicById(String id) {
        logger.info("Finding clinic details for id: {}", id);
        Optional<ClinicDetails> dbClinic = clinicRepository.findById(id);

        if (dbClinic.isEmpty()) {
            logger.error("No clinic details for id: {}", id);
            throw new RuntimeException("Clinic not found");
        }

        return dbClinic.get();
    }

    public ClinicDetails updateClinicDetails(ClinicDetails clinicDetails) {
        ClinicDetails updatedClinic = getClinicById(clinicDetails.getId());

        logger.info("Updating clinic details");

        mergeNonNullFields(clinicDetails, updatedClinic);
        clinicRepository.save(updatedClinic);

        return updatedClinic;
    }

    public void deleteClinicById(String id) {
        logger.info("Deleting clinic details");
        clinicRepository.deleteById(id);
    }

    public List<ClinicDetails> getNearByClinics(String city, String zipcode) {
        GeoLocation geoLocation = geocodingService.getLatLongForLocation(city, zipcode);

        Point location = new Point(geoLocation.longitude(), geoLocation.latitude());
        Query query = new Query(Criteria.where(ClinicDetails.LOCATION_FIELD).nearSphere(location));

        logger.info("Fetching nearby clinics...");
        return mongoTemplate.find(query, ClinicDetails.class);
    }
}
