package com.assesment.countrydata.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private Name name; // Defined a nested class to represent the "name" field

    @JsonProperty("region")
    private String region;

    @JsonProperty("borders")
    private List<String> borders;

    @JsonProperty("population")
    private long population;

    @JsonProperty("area")
    private double area;

    public static class Name {
        @Getter
        @Setter
        private String common;
        private String official;
        private NativeName nativeName;
    }

    public static class NativeName {
        private String eng;
    }
}