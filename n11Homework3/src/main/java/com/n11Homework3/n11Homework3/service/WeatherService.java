package com.n11Homework3.n11Homework3.service;

import com.n11Homework3.n11Homework3.client.WeatherApiClient;
import com.n11Homework3.n11Homework3.exception.BadRequestException;
import com.n11Homework3.n11Homework3.exception.InternalServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;



@Service
public class WeatherService {

    private final WeatherApiClient weatherApiClient;

    public WeatherService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = Objects.requireNonNull(weatherApiClient, "weatherApiClient must not be null");
    }

    public ResponseEntity<String> getWeatherForecast(String country, String city, int forecastDuration) {
        try {
            validateInputParameters(country, city, forecastDuration);
            String forecastData = weatherApiClient.getWeatherForecast(country, city, forecastDuration);
            return ResponseEntity.ok(forecastData);
        } catch (IllegalArgumentException | BadRequestException e) {
            throw e; // Re-throwing specific exceptions
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch weather forecast data");
        }
    }

    private void validateInputParameters(String country, String city, int forecastDuration) {
        if (forecastDuration <= 0) {
            throw new IllegalArgumentException("Forecast duration should be greater than zero");
        }

        if (city == null || city.trim().isEmpty() || country == null || country.trim().isEmpty()) {
            throw new BadRequestException("City and country parameters cannot be null or empty");
        }
    }
}
