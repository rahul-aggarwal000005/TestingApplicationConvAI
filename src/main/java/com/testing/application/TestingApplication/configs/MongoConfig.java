package com.testing.application.TestingApplication.configs;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String mongoHost = System.getProperty("MONGO_HOST", "localhost");
        String mongoPort = System.getProperty("MONGO_PORT", "27017");
        String mongoAtlasURI = System.getProperty("MONGO_ATLAS_URI",  null);
        String connectionString = "mongodb://" + mongoHost + ":" + mongoPort;
        if (mongoAtlasURI != null) {
            connectionString = mongoAtlasURI;
        }
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        String databaseName = System.getProperty("MONGO_DB", "testapp");
        return new MongoTemplate(mongoClient, databaseName);
    }
}
