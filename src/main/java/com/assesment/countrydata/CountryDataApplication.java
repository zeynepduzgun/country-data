package com.assesment.countrydata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.assesment.countrydata", "com.assesment.countrydata.exception" })
@SpringBootApplication
public class CountryDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryDataApplication.class, args);
	}

}
