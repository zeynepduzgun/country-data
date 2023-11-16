package com.assesment.countrydata.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;

import com.assesment.countrydata.model.Country;

@Service
public class CountryDataService implements HealthIndicator {
    private static final Logger logger = LoggerFactory.getLogger(CountryDataService.class);

    @Value("${api.url}")
    private String apiUrl;

    private RestTemplate restTemplate;
    private List<Country> countries;

    public CountryDataService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Country> getCountries() {
        // Ensure that countries are initialized before returning
        if (countries == null) {
            countries = fetchDataFromApi();
        }
        return countries;
    }

    private List<Country> fetchDataFromApi() {
        try {
            // Send a GET request and map the response to an array of Country objects
            Country[] countryArray = restTemplate.getForObject(apiUrl, Country[].class);
            return countryArray != null ? Arrays.asList(countryArray) : new ArrayList<>();
        } catch (HttpStatusCodeException e) {
            logger.error("Error occurred while fetching data from the API: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Country> getCountriesByPopulationDensity() {
        List<Country> sortedCountries = new ArrayList<>(getCountries());
        sortedCountries.sort(Comparator.comparingDouble(country -> -country.getPopulation() / country.getArea()));

        sortedCountries.forEach(country -> logger.info(country.getName().getCommon() + " Population Density: " +
                (country.getPopulation() / country.getArea())));

        return sortedCountries;
    }

    public String getCountryWithMostBorderingCountries(String region) {
        List<Country> countriesByRegion = getCountries().stream()
                .filter(country -> country.getRegion().equalsIgnoreCase(region))
                .toList();

        Map<String, Integer> countryBorderCount = new HashMap<>();

        for (Country country : countriesByRegion) {
            if (country.getBorders() != null) {
                countryBorderCount.put(country.getName().getCommon(), country.getBorders().size());

            }
        }

        String mostBorderingCountries = countryBorderCount.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        logger.info("Country in " + region + " with the most bordering countries in a different region: " +
                mostBorderingCountries);

        return mostBorderingCountries;
    }

    @Override
    public Health health() {
        if (isCountryDataServiceWorks()) {
            return Health.up().withDetail("Country Data Service", "Service is running").build();
        }
        return Health.down().withDetail("Country Data Service", "Service is not available").build();
    }


    private boolean isCountryDataServiceWorks() {
        // written only for representation for health check.
        double chance = ThreadLocalRandom.current().nextDouble();
        return chance > 0.9;
    }
}