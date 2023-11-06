package com.assesment.countrydata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CountryDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryDataApplication.class, args);
	}

}
