package com.assesment.countrydata.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.slf4j.Logger;

import com.assesment.countrydata.model.Country;
import com.assesment.countrydata.service.CountryDataService;

@RestController
@RequestMapping("/api/countries")
public class CountryDataController {
    private final CountryDataService countryDataService;
    private static final Logger logger = LoggerFactory.getLogger(CountryDataController.class);

    @Autowired
    public CountryDataController(CountryDataService countryDataService) {
        this.countryDataService = countryDataService;
    }

    @GetMapping("/population-density")
    public List<Country> getCountriesByPopulationDensity() {
        try {
            return countryDataService.getCountriesByPopulationDensity();

        } catch (HttpStatusCodeException e) {
            logger.error("Failed to retrieve data from the API: {}", e.getMessage());
            throw e; // Re-throw the exception for global error handling, or return an error response
        }
    }

    @GetMapping("/most-bordering-country")
    public String getCountryWithMostBorderingCountries(
            @RequestParam String region) {
        try {
            return countryDataService.getCountryWithMostBorderingCountries(region);

        } catch (HttpStatusCodeException e) {
            logger.error("Failed to retrieve data from the API: {}", e.getMessage());
            throw e; // Re-throw the exception for global error handling, or return an error response
        }
    }
}
