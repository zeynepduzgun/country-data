package com.assesment.countrydata.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;

import com.assesment.countrydata.model.Country;

@Service
public class CountryDataService {
    private static final Logger logger = LoggerFactory.getLogger(CountryDataService.class);

    @Value("${api.url}")
    private String apiUrl;

    private RestTemplate restTemplate;
    private List<Country> countries;

    public CountryDataService() {
        this.restTemplate = new RestTemplate();
        this.countries = fetchDataFromApi();
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

    public List<Country> getCountries() {
        return countries;
    }

    public List<Country> getCountriesByPopulationDensity() {
        List<Country> sortedCountries = new ArrayList<>(countries);
        sortedCountries.sort(Comparator.comparingDouble(country -> -country.getPopulation() / country.getArea()));

        sortedCountries.forEach(country -> logger.info(country.getName().getCommon() + " Population Density: " +
                (country.getPopulation() / country.getArea())));

        return sortedCountries;
    }

    public String getCountryWithMostBorderingCountries(String region) {
        Map<String, Integer> countryBorderCount = new HashMap<>();
        List<Country> countriesByRegion = countries.stream()
                .filter(country -> country.getRegion().equalsIgnoreCase(region))
                .toList();

        for (Country country : countriesByRegion) {
            countryBorderCount.put(country.getName().getCommon(), country.getBorders().size());
        }

        String mostBorderingCountries = countryBorderCount.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        logger.info("Country in " + region + " with the most bordering countries in a different region: " +
                mostBorderingCountries);

        return mostBorderingCountries;
    }
}