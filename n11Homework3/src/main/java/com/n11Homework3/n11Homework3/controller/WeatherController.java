package com.n11Homework3.n11Homework3.controller;

import com.n11Homework3.n11Homework3.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private  final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/daily/{country}/{city}")
    public ResponseEntity<String> getDailyWeather(@PathVariable @NotBlank(message = "Country cannot be blank") String country,
                                                  @PathVariable @NotBlank(message = "City cannot be blank") String city) {
        return weatherService.getWeatherForecast(country, city, 1);
    }


    @GetMapping("/weekly/{country}/{city}")
    public ResponseEntity<String> getWeeklyWeather(@PathVariable @NotBlank(message = "Country cannot be blank") String country,
                                                   @PathVariable @NotBlank(message = "City cannot be blank") String city) {
        return weatherService.getWeatherForecast(country, city, 7*8);
    }


    @GetMapping("/monthly/{country}/{city}")
    public ResponseEntity<String> getMonthlyWeather(@PathVariable @NotBlank(message = "Country cannot be blank") String country,
                                                    @PathVariable @NotBlank(message = "City cannot be blank") String city) {
        return weatherService.getWeatherForecast(country, city, 30*8);
    }

}