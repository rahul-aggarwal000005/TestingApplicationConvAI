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
        Dotenv dotenv = Dotenv.load();
        System.setProperty(MONGO_HOST, dotenv.get(MONGO_HOST));
        System.setProperty(MONGO_PORT, dotenv.get(MONGO_PORT));
        System.setProperty(MONGO_DB, dotenv.get(MONGO_DB));
        System.setProperty(MONGO_AUTH_DB, dotenv.get(MONGO_AUTH_DB));
    }
}
