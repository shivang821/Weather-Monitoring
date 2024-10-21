package com.weather.monitoring.weather_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WeatherScheduler {

    @Autowired
    private WeatherDataService weatherDataService;
    @Autowired
    private WeatherSummaryService dailySummaryService;

    @Scheduled(fixedRate = 120000) // Runs every 2 minute
    public void updateWeatherData() {
        List<String> cities = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

        cities.forEach(city -> {
            try {
                weatherDataService.getWeatherData(city);
                dailySummaryService.generateDailySummary(city);
            } catch (Exception e) {
                // Handle exceptions (e.g., log the error)
                e.printStackTrace();
            }
        });
    }
}

