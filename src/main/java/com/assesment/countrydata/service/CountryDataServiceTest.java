package com.assesment.countrydata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.assesment.countrydata.model.Country;

public class CountryDataServiceTest {

        @InjectMocks
        private CountryDataService countryDataService;

        @Mock
        private RestTemplate restTemplate;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testGetCountries() {
                // Arrange
                Country[] sampleCountries = { new Country("Country1", null, null, 0, 0),
                                new Country("Country2", null, null, 0, 0) };
                when(restTemplate.getForObject(any(String.class), any(Class.class)))
                                .thenReturn(sampleCountries);

                // Act
                List<Country> countries = countryDataService.getCountries();

                // Assert
                assertEquals(2, countries.size());
                assertEquals("Country1", countries.get(0).getName());
                assertEquals("Country2", countries.get(1).getName());
        }

        @Test
        public void testGetCountriesByPopulationDensity() {
                // Arrange
                List<Country> sampleCountries = Arrays.asList(
                                new Country("Country1", "Asia", null, 5000, 2500),
                                new Country("Country2", "Europe", null, 5100, 3000));

                when(restTemplate.getForObject(any(String.class), any(Class.class)))
                                .thenReturn(sampleCountries);

                // Act
                List<Country> countries = countryDataService.getCountries();

                // Assert
                assertEquals(2, countries.size());
                assertEquals("Country1", countries.get(0).getName()); // Highest population density
                assertEquals("Country2", countries.get(1).getName());
        }

        @Test
        public void testGetCountryWithMostBorderingCountries() {
                // Arrange
                List<Country> sampleCountries = Arrays.asList(
                                new Country("Country1", "Asia",
                                                Arrays.asList("Border1", "Border2", "Border3", "Border4"), 0, 0),
                                new Country("Country2", "Europe", Arrays.asList("Border5", "Border6"), 0, 0),
                                new Country("Country3", "America", Arrays.asList("Border7", "Border5"), 0, 0));

                when(restTemplate.getForObject(any(String.class), any(Class.class)))
                                .thenReturn(sampleCountries);

                // Act
                String country = countryDataService.getCountryWithMostBorderingCountries("Asia");

                // Assert
                assertEquals("Country1", country);
        }
}
