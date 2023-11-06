package com.assesment.countrydata.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private String name;
    private String region;
    private List<String> borders;
    private double population;
    private double area;

}
