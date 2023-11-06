# Country Data Application

The **Country Data Application** is a Java Spring Boot application that retrieves and processes information about countries from a remote API. It offers two primary features: sorting countries by population density and identifying the Asian country with the most bordering countries from different regions.

## Features

1. **Sorted List of Countries by Population Density**

   - Fetches country data from a remote API.
   - Calculates and sorts countries by population density (population divided by land area) in descending order.
   - Displays the sorted list with population density information.

2. **Identifying the Asian Country with the Most Bordering Countries from Different Regions**
   - Filters and processes country data to identify Asian countries.
   - Counts the number of bordering countries that belong to a different region for each Asian country.
   - Determines and displays the Asian country with the most bordering countries from different regions.

# Technical Details:

The application is built using the Spring Boot framework, which simplifies the development of Java-based web applications.
It uses the Spring Framework's RestTemplate to make HTTP requests to the remote API and retrieve country data.
Exception handling is incorporated, with a global exception handler to ensure secure error responses.
The application is designed with modularity and clean code practices in mind, facilitating future maintenance and extension.
It provides an endpoint for each of the two main features, which can be accessed through HTTP requests.
The application's configuration, including the remote API URL, can be specified in the application.properties file.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or a compatible version.
- Apache Maven. You can download it from [Maven's official website](https://maven.apache.org/download.cgi).

### Installation

1. Clone the repository:

```shell
git clone https://github.com/yourusername/country-data.git
```

Navigate to the project directory:

```shell
cd country-data
```

Build the application:

```shell
mvn clean package
```

This command will compile the code, run tests, and package the application into a JAR file in the target directory.

Run the application:
After a successful build, you can run the application with the following command:

```shell
java -jar target/country-data-0.0.1-SNAPSHOT.jar
```

The application will start, and you can access its features as described below.

Usage
Sorted List of Countries by Population Density
To retrieve a sorted list of countries by population density, make an HTTP GET request to the following endpoint:

```bash
http://localhost:8080/api/countries/population-density
```

Identifying the Asian Country with the Most Bordering Countries from Different Regions
To identify the Asian country with the most bordering countries from different regions, make an HTTP GET request to the following endpoint:

```bash
http://localhost:8080/api/countries/most-bordering-country
```

You can provide an optional query parameter region to specify the region of interest. The default region is "Asia."

For example:

```bash
http://localhost:8080/api/countries/most-bordering-country?region=Europe
```

Configuration
You can configure the application by editing the application.properties or application.yml file. Customize the API URL, server port, or other properties as needed.
