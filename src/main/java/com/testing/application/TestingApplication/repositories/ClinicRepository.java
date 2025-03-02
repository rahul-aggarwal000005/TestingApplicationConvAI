package com.testing.application.TestingApplication.repositories;

import com.testing.application.TestingApplication.models.ClinicDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends MongoRepository<ClinicDetails, String> {
}
