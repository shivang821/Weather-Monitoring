package com.weather.monitoring.weather_backend.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.monitoring.weather_backend.Model.WeatherData;
import com.weather.monitoring.weather_backend.Repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//package com.weather.monitoring.weather_backend.Service;
//import com.weather.monitoring.weather_backend.Model.WeatherData;
//import com.weather.monitoring.weather_backend.Repository.WeatherDataRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class WeatherDataService {
//
//    @Autowired
//    private WeatherDataRepository weatherDataRepository;
//
//    public WeatherData saveOrUpdateWeatherData(String city, double temperature, double feelsLike, String weatherCondition) {
//        LocalDate today = LocalDate.now();
//        WeatherData weatherData = weatherDataRepository.findByCityAndDate(city, today).orElse(new WeatherData());
//
//        weatherData.setCity(city);
//        weatherData.setDate(today);
//        weatherData.setTemperature(temperature);
//        weatherData.setFeelsLike(feelsLike);
//        weatherData.setWeatherCondition(weatherCondition);
//
//        return weatherDataRepository.save(weatherData);
//    }
//
//    public List<WeatherData> getCurrentWeatherForAllCities(List<String> cities) {
//        LocalDate today = LocalDate.now();
//        return weatherDataRepository.findByDateAndCityIn(today, cities);
//    }
//}
@Service
public class WeatherDataService {

    @Value("${openweather.api.key}")
    private String apiKey;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WeatherDataRepository weatherDataRepository;

    // Fetch weather data for a single city
    public WeatherData getWeatherData(String city) throws Exception {
//        LocalDate today = LocalDate.now();
//
//        // Check if data already exists for the city today
//        Optional<WeatherData> existingWeatherData = weatherDataRepository.findByCityAndDate(city, today);
//
//        if (existingWeatherData.isPresent()) {
//            return existingWeatherData.get(); // Return if data exists
//        } else {
//            // Fetch new data from OpenWeather API
//            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            JsonNode root = new ObjectMapper().readTree(response.getBody());
//
//            // Parse the response and create a WeatherData object
//            WeatherData weatherData = new WeatherData();
//            weatherData.setCity(city);
//            weatherData.setWeatherCondition(root.get("weather").get(0).get("main").asText());
//            weatherData.setTemperature(root.get("main").get("temp").asDouble() - 273.15); // Kelvin to Celsius
//            weatherData.setFeelsLike(root.get("main").get("feels_like").asDouble() - 273.15);
//            weatherData.setDate(today);
//
//            // Save new weather data
//            weatherDataRepository.save(weatherData);
//            return weatherData;
//        }


        LocalDate today = LocalDate.now();

        // Check if the WeatherData entry for today exists
        WeatherData existingData = weatherDataRepository.findByCityAndDate(city, today).orElse(null);

        // Fetch new data from OpenWeather API
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Parse the JSON response
        JsonNode root = objectMapper.readTree(response.getBody());

        double currentTemperature = root.get("main").get("temp").asDouble() - 273.15; // Convert Kelvin to Celsius
        String weatherCondition = root.get("weather").get(0).get("main").asText();

        if (existingData != null) {
            // Update existing data
            System.out.println(currentTemperature+" "+existingData.getCity());
            List<Double> temperatureList = getTemperatureList(existingData.getTemperatures());
            temperatureList.add(currentTemperature); // Append new temperature
            existingData.setTemperatures(objectMapper.writeValueAsString(temperatureList)); // Convert list back to JSON string

            // Update other fields
            existingData.setWeatherCondition(weatherCondition);
            existingData.setFeelsLike(root.get("main").get("feels_like").asDouble() - 273.15);
            existingData.setTimestamp(LocalDate.now()); // Or use LocalDateTime if needed
            weatherDataRepository.save(existingData);
            return existingData;
        } else {
            // Create new entry
            WeatherData weatherData = new WeatherData();
            weatherData.setCity(city);
            weatherData.setDate(today);
            weatherData.setTemperatures(objectMapper.writeValueAsString(List.of(currentTemperature))); // Start new list
            weatherData.setWeatherCondition(weatherCondition);
            weatherData.setFeelsLike(root.get("main").get("feels_like").asDouble() - 273.15);
            weatherDataRepository.save(weatherData);
            return weatherData;
        }
    }
    private List<Double> getTemperatureList(String temperatures) throws Exception {
        if (temperatures != null && !temperatures.isEmpty()) {
            return objectMapper.readValue(temperatures, List.class);
        }
        return new ArrayList<>();
    }
    // Fetch weather data for all specified cities
    public List<WeatherData> getWeatherDataForAllCities(List<String> cities) throws Exception {
        List<WeatherData> weatherDataList = new ArrayList<>();

        for (String city : cities) {
            WeatherData weatherData = getWeatherData(city);
            weatherDataList.add(weatherData);
        }

        return weatherDataList;
    }
}