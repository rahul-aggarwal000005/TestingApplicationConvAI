package com.testing.application.TestingApplication.services;

import com.testing.application.TestingApplication.dto.GeoLocation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeocodingService {

    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);

    private static final String CITY = "city";
    private static final String POSTAL_CODE = "postalcode";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search";

    public GeoLocation getLatLongForLocation(String city, String zipCode) {
        logger.info("Fetching geo location details for the location city: {}, zipCode: {}", city, zipCode);
        if ((city == null || city.trim().isEmpty()) && (zipCode == null || zipCode.trim().isEmpty())) {
            throw new IllegalArgumentException("city and zipcode cannot be null or empty.");
        }

        String url = buildGeocodingUrl(city, zipCode);
        return fetchCoordinatesFromApi(url);
    }

    /**
     * Builds the Nominatim API URL with the given parameters.
     */

    private String buildGeocodingUrl(String city, String zipCode) {
        logger.info("Building URL...");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(NOMINATIM_BASE_URL)
                .queryParam("format", "json");

        if (city != null && !city.isEmpty()) {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            builder.queryParam(CITY, encodedCity);
        }
        if (zipCode != null && !zipCode.isEmpty()) {
            builder.queryParam(POSTAL_CODE, zipCode);
        }

        return builder.toUriString();
    }

    /**
     * Calls the Nominatim API and extracts latitude and longitude from the response.
     */
    private GeoLocation fetchCoordinatesFromApi(String url) {
        try {
            logger.info("Requesting: {}", url);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JSONArray jsonArray = new JSONArray(response.getBody());
            if (jsonArray.isEmpty()) {
                throw new RuntimeException("No location found for the given parameters.");
            }

            JSONObject location = jsonArray.getJSONObject(0);

            double latitude = location.getDouble(LATITUDE);
            double longitude = location.getDouble(LONGITUDE);
            logger.info("GeoLocation(latitude: {}, longitude: {})", latitude, longitude);

            return new GeoLocation(latitude, longitude);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch coordinates: " + e.getMessage(), e);
        }
    }


}
