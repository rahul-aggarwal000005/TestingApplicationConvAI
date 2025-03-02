package com.testing.application.TestingApplication.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EnvConfig {

    /* Mongo Configs */
    private static final String MONGO_HOST ="MONGO_HOST";
    private static final String MONGO_PORT = "MONGO_PORT";
    private static final String MONGO_DB = "MONGO_DB";
    private static final String MONGO_AUTH_DB = "MONGO_AUTH_DB";

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        setSystemProperty(MONGO_HOST, dotenv.get(MONGO_HOST, "localhost"));  // Default to localhost
        setSystemProperty(MONGO_PORT, dotenv.get(MONGO_PORT, "27017"));     // Default MongoDB port
        setSystemProperty(MONGO_DB, dotenv.get(MONGO_DB, "testdb"));        // Default DB name
        setSystemProperty(MONGO_AUTH_DB, dotenv.get(MONGO_AUTH_DB, "admin")); // Default auth DB
    }

    private static void setSystemProperty(String key, String value) {
        if (value != null) {
            System.setProperty(key, value);
        }
    }
}
