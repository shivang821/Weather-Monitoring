//package com.weather.monitoring.weather_backend.Service;
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class WeatherUpdateScheduler {
//
//    @Autowired
//    private WeatherDataService weatherDataService;
//
//    @Autowired
//    private WeatherSummaryService weatherSummaryService;
//
//    @Scheduled(fixedRate = 300000) // Every 5 minutes
//    public void fetchAndStoreWeatherData() {
//        // List of cities
//        List<String> cities = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");
//
//        for (String city : cities) {
//            // Fetch data from OpenWeatherMap API (write the API call logic)
//            WeatherApiResponse response = callWeatherApi(city);
//
//            // Convert temperatures from Kelvin to Celsius
//            double temperature = response.getTemp() - 273.15;
//            double feelsLike = response.getFeelsLike() - 273.15;
//
//            // Save the current weather data
//            weatherDataService.saveOrUpdateWeatherData(city, temperature, feelsLike, response.getWeatherCondition());
//        }
//    }
//}
//
