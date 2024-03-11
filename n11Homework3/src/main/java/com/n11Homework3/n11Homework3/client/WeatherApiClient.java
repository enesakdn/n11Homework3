package com.n11Homework3.n11Homework3.client;

import com.n11Homework3.n11Homework3.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherApiClient {

    private final String apiKey;
    private final String apiUrl;
    private final RestOperations restOperations;

    public WeatherApiClient(@Value("${weather.api.key}") String apiKey,
                            @Value("${weather.api.url}") String apiUrl,
                            RestOperations restOperations) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.restOperations = restOperations;
    }

    public String getWeatherForecast(String country, String city, int numberOfDays) {
        String url = buildUrl(country, city, numberOfDays);

        try {
            return restOperations.getForObject(url, String.class);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch weather forecast data from external API");
        }
    }

    private String buildUrl(String country, String city, int numberOfDays) {
        return UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("q", String.format("%s,%s", city, country))
                .queryParam("cnt", numberOfDays)
                .queryParam("APPID", apiKey)
                .queryParam("units", "metric")
                .build().toUriString();
    }
}
