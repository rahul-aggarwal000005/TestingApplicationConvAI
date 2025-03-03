package com.testing.application.TestingApplication.repositories;

import com.testing.application.TestingApplication.models.ClinicDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends MongoRepository<ClinicDetails, String> {

    @Query("{ 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: ?2 } } }")
    List<ClinicDetails> findNearbyClinics(double longitude, double latitude, double maxDistance);

    @Query("{ 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: [?0, ?1] } } } }")
    List<ClinicDetails> findNearbyClinics(double longitude, double latitude);
}
