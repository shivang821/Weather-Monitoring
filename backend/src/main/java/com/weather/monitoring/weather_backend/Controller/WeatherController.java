package com.weather.monitoring.weather_backend.Controller;

import com.weather.monitoring.weather_backend.Model.WeatherData;
import com.weather.monitoring.weather_backend.Service.WeatherDataService;
//import com.weather.monitoring.weather_backend.Service.WeatherService;
//import com.weather.monitoring.weather_backend.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    private WeatherDataService weatherDataService;

    private static final List<String> CITIES = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad","Jaipur");

    // Fetch weather data for a specific city
    @GetMapping("/{city}")
    public ResponseEntity<WeatherData> getWeatherDataForCity(@PathVariable String city) {
        try {
            WeatherData weatherData = weatherDataService.getWeatherData(city);
            return ResponseEntity.ok(weatherData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Fetch weather data for all specified cities
    @GetMapping("/")
    public ResponseEntity<List<WeatherData>> getWeatherDataForAllCities() {
        try {
            List<WeatherData> weatherDataList = weatherDataService.getWeatherDataForAllCities(CITIES);
            return ResponseEntity.ok(weatherDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
