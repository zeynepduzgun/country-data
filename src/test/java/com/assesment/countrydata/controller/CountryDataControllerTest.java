package com.assesment.countrydata.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpStatusCodeException;

import com.assesment.countrydata.CountryDataApplication;
import com.assesment.countrydata.model.Country;
import com.assesment.countrydata.service.CountryDataService;

@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@SpringBootTest(classes = CountryDataApplication.class)
public class CountryDataControllerTest {
    @InjectMocks
    private CountryDataController countryDataController;

    @Mock
    private CountryDataService countryDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(countryDataController);
    }

    @Test
    public void testGetCountriesByPopulationDensity() {
        // Arrange
        List<Country> mockCountryList = List.of(
                new Country("Country1", "Asia", null, 1000000, 1000),
                new Country("Country2", "Asia", null, 800000, 2000));

        when(countryDataService.getCountriesByPopulationDensity()).thenReturn(mockCountryList);

        // Act
        List<Country> result = countryDataController.getCountriesByPopulationDensity();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Country1", result.get(0).getName());
        assertEquals("Country2", result.get(1).getName());
    }

    @Test
    public void testGetCountryWithMostBorderingCountries() {
        // Arrange
        String mockRegion = "Asia";
        String mockCountryName = "Country1";

        when(countryDataService.getCountryWithMostBorderingCountries(mockRegion)).thenReturn(mockCountryName);

        // Act
        String result = countryDataController.getCountryWithMostBorderingCountries(mockRegion);

        // Assert
        assertEquals(mockCountryName, result);
    }

    @Test
    public void testGetCountriesByPopulationDensityError() {
        // Arrange
        when(countryDataService.getCountriesByPopulationDensity()).thenThrow(new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
        });

        // Act and Assert
        try {
            countryDataController.getCountriesByPopulationDensity();
        } catch (HttpStatusCodeException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    public void testGetCountryWithMostBorderingCountriesError() {
        // Arrange
        String mockRegion = "Asia";

        when(countryDataService.getCountryWithMostBorderingCountries(mockRegion))
                .thenThrow(new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
                });

        // Act and Assert
        try {
            countryDataController.getCountryWithMostBorderingCountries(mockRegion);
        } catch (HttpStatusCodeException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}
