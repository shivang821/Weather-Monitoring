//package com.weather.monitoring.weather_backend.Service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.weather.monitoring.weather_backend.Model.WeatherData;
//import com.weather.monitoring.weather_backend.Model.WeatherSummary;
//import com.weather.monitoring.weather_backend.Repository.WeatherDataRepository;
//import com.weather.monitoring.weather_backend.Repository.WeatherSummaryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class WeatherService {
//
//    @Autowired
//    private WeatherDataRepository weatherDataRepository;  // Repository to store current weather
//
//    @Autowired
//    private WeatherSummaryRepository weatherSummaryRepository;  // Repository to store daily summaries
//
//    private final String apiKey = "your_openweathermap_api_key";
//
//    public WeatherData getWeatherData(String city) {
//        Optional<WeatherData> existingWeatherData = weatherDataRepository.findByCityAndDate(city, LocalDateTime.now().toLocalDate());
//
//        if (existingWeatherData.isPresent()) {
//            // If data already exists for today, return the existing data
//            return existingWeatherData.get();
//        }
//
//        // If no data exists for the current day, fetch from OpenWeatherMap API
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
//        RestTemplate restTemplate = new RestTemplate();
//
//        try {
//            // Make the API call
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            JsonNode root = new ObjectMapper().readTree(response.getBody());
//
//            // Parse the response and set weather data
//            WeatherData weatherData = new WeatherData();
//            weatherData.setCity(city);
//            weatherData.setWeatherCondition(root.get("weather").get(0).get("main").asText());
//            weatherData.setTemperature(root.get("main").get("temp").asDouble() - 273.15);  // Kelvin to Celsius
//            weatherData.setFeelsLike(root.get("main").get("feels_like").asDouble() - 273.15);
//            weatherData.setDate(LocalDate.now());
//
//            // Save the weather data to the database
//            weatherDataRepository.save(weatherData);
//
//            return weatherData;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error fetching weather data from API");
//        }
//    }
//
//    public void updateWeatherSummary(String city) {
//        LocalDate today = LocalDate.now();
//        Optional<WeatherSummary> existingSummary = weatherSummaryRepository.findByCityAndDate(city, today);
//
//        if (existingSummary.isEmpty()) {
//            // If no summary for today exists, create a new one
//            WeatherData weatherData = getWeatherData(city);
//
//            WeatherSummary summary = new WeatherSummary();
//            summary.setCity(city);
//            summary.setDate(today);
//            summary.setAverageTemperature(weatherData.getTemperature());
//            summary.setMaxTemperature(weatherData.getTemperature());
//            summary.setMinTemperature(weatherData.getTemperature());
//            summary.setDominantWeatherCondition(weatherData.getWeatherCondition());
//
//            weatherSummaryRepository.save(summary);
//        } else {
//            // If a summary for today exists, update it
//            WeatherSummary summary = existingSummary.get();
//            WeatherData weatherData = getWeatherData(city);
//
//            // Update temperature aggregates
//            summary.setMaxTemperature(Math.max(summary.getMaxTemperature(), weatherData.getTemperature()));
//            summary.setMinTemperature(Math.min(summary.getMinTemperature(), weatherData.getTemperature()));
//            summary.setAverageTemperature((summary.getAverageTemperature() + weatherData.getTemperature()) / 2);
//            summary.setDominantWeatherCondition(weatherData.getWeatherCondition());
//
//            weatherSummaryRepository.save(summary);
//        }
//    }
//}