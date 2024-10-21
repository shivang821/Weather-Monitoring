package com.weather.monitoring.weather_backend.Service;

import com.weather.monitoring.weather_backend.Model.WeatherData;
import com.weather.monitoring.weather_backend.Model.WeatherSummary;
import com.weather.monitoring.weather_backend.Repository.WeatherDataRepository;
import com.weather.monitoring.weather_backend.Repository.WeatherSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class WeatherService {
//    @Autowired
//    private WeatherDataRepository weatherDataRepository;
//
//    @Autowired
//    private WeatherSummaryRepository weatherSummaryRepository;
//
//    @Value("${openweathermap.api.key}")
//    private String apiKey;
//
//    // Method to fetch weather from OpenWeatherMap API
//    public WeatherData fetchWeatherData(String city) throws JsonProcessingException {
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        // Parse the JSON response
//        JsonNode root = new ObjectMapper().readTree(response.getBody());
//
//        WeatherData weatherData = new WeatherData();
//        weatherData.setCity(city);
//        weatherData.setWeatherCondition(root.get("weather").get(0).get("main").asText());
//        weatherData.setTemperature(root.get("main").get("temp").asDouble() - 273.15);  // Kelvin to Celsius
//        weatherData.setFeelsLike(root.get("main").get("feels_like").asDouble() - 273.15);
//        weatherData.setTimestamp(LocalDateTime.now());
//
//        weatherDataRepository.save(weatherData);
//        return weatherData;
//    }
//
//    // Roll-up aggregate calculations
//    public WeatherSummary calculateDailySummary(String city) {
//        LocalDate today = LocalDate.now();
//        LocalDateTime startOfDay = today.atStartOfDay();
//        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
//
//        List<WeatherData> weatherDataList = weatherDataRepository.findByCityAndTimestampBetween(city, startOfDay, endOfDay);
//
//        double averageTemp = weatherDataList.stream().mapToDouble(WeatherData::getTemperature).average().orElse(0);
//        double maxTemp = weatherDataList.stream().mapToDouble(WeatherData::getTemperature).max().orElse(0);
//        double minTemp = weatherDataList.stream().mapToDouble(WeatherData::getTemperature).min().orElse(0);
//
//        // Dominant weather condition based on frequency
//        Map<String, Long> conditionCounts = weatherDataList.stream()
//                .collect(Collectors.groupingBy(WeatherData::getWeatherCondition, Collectors.counting()));
//
//        String dominantCondition = Collections.max(conditionCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
//
//        WeatherSummary summary = new WeatherSummary();
//        summary.setCity(city);
//        summary.setDate(today);
//        summary.setAverageTemperature(averageTemp);
//        summary.setMaxTemperature(maxTemp);
//        summary.setMinTemperature(minTemp);
//        summary.setDominantWeatherCondition(dominantCondition);
//
//        weatherSummaryRepository.save(summary);
//        return summary;
//    }
//
//    // Alert on temperature thresholds
//    public void checkThresholds(double temp, double threshold) {
//        if (temp > threshold) {
//            // Trigger alert (console, email, etc.)
//            System.out.println("Alert! Temperature exceeded " + threshold + " degrees.");
//        }
//    }
//}

//@Service
//public class WeatherSummaryService {
//
//    @Autowired
//    private WeatherSummaryRepository weatherSummaryRepository;
//
//    public WeatherSummary saveOrUpdateWeatherSummary(String city, double avgTemp, double maxTemp, double minTemp, String dominantCondition) {
//        LocalDate today = LocalDate.now();
//        WeatherSummary weatherSummary = weatherSummaryRepository.findByCityAndDate(city, today)
//                .orElse(new WeatherSummary());
//
//        weatherSummary.setCity(city);
//        weatherSummary.setDate(today);
//        weatherSummary.setAverageTemperature(avgTemp);
//        weatherSummary.setMaxTemperature(maxTemp);
//        weatherSummary.setMinTemperature(minTemp);
//        weatherSummary.setDominantWeatherCondition(dominantCondition);
//
//        return weatherSummaryRepository.save(weatherSummary);
//    }
//
//    public List<WeatherSummary> getWeatherSummaryForPastDays(String city, int days) {
//        LocalDate today = LocalDate.now();
//        LocalDate pastWeek = today.minusDays(days);
//        return weatherSummaryRepository.findByCityAndDateBetween(city, pastWeek, today);
//    }
//}
//



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class WeatherSummaryService {

    @Autowired
    private WeatherSummaryRepository weatherSummaryRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;
//    private final String apiKey = "YOUR_API_KEY"; // Replace with your actual API key
    @Value("${openweather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherSummary getWeatherSummary(String city) throws Exception {
        LocalDate today = LocalDate.now();

        // Check if the summary for today already exists
        WeatherSummary existingSummary = weatherSummaryRepository.findByCityAndDate(city, today).orElse(null);

        if (existingSummary != null) {
            return existingSummary; // Return existing summary
        }

        // Fetch data from OpenWeather API
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Parse the JSON response
        JsonNode root = objectMapper.readTree(response.getBody());

        // Create a new WeatherSummary object
        WeatherSummary weatherSummary = new WeatherSummary();
        weatherSummary.setCity(city);
        weatherSummary.setMinTemp(root.get("main").get("temp_min").asDouble() - 273.15); // Convert Kelvin to Celsius
        weatherSummary.setMaxTemp(root.get("main").get("temp_max").asDouble() - 273.15);
        weatherSummary.setAvgTemp((weatherSummary.getMinTemp() + weatherSummary.getMaxTemp()) / 2); // Calculate average
        weatherSummary.setWeatherCondition(root.get("weather").get(0).get("main").asText());
        weatherSummary.setWindSpeed(root.get("wind").get("speed").asDouble());
        weatherSummary.setDate(today);

        // Save the new summary to the database
        weatherSummaryRepository.save(weatherSummary);
        return weatherSummary;
    }
    public List<WeatherSummary> getPastWeekWeatherSummary(String city) throws Exception {
        List<WeatherSummary> summaries = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 1; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            WeatherSummary existingSummary = weatherSummaryRepository.findByCityAndDate(city, date).orElse(null);

            if (existingSummary == null) {
                // Fetch data from OpenWeather API for the past week summary
                String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                // Parse the JSON response
                JsonNode root = objectMapper.readTree(response.getBody());

                // Create a new WeatherSummary object
                WeatherSummary weatherSummary = new WeatherSummary();
                weatherSummary.setCity(city);
                weatherSummary.setMinTemp(root.get("main").get("temp_min").asDouble() - 273.15);
                weatherSummary.setMaxTemp(root.get("main").get("temp_max").asDouble() - 273.15);
                weatherSummary.setAvgTemp((weatherSummary.getMinTemp() + weatherSummary.getMaxTemp()) / 2); // Calculate average
                weatherSummary.setWeatherCondition(root.get("weather").get(0).get("main").asText());
                weatherSummary.setWindSpeed(root.get("wind").get("speed").asDouble());
                weatherSummary.setDate(date);

                // Save the new summary to the database
                weatherSummaryRepository.save(weatherSummary);
                summaries.add(weatherSummary);
            } else {
                summaries.add(existingSummary); // Add existing summary to the list
            }
        }

        return summaries;
    }
    public void generateDailySummary(String city) throws Exception {
        LocalDate today = LocalDate.now();

        // Check if today's summary already exists
        Optional<WeatherSummary> existingSummary = weatherSummaryRepository.findByCityAndDate(city, today);

        if (existingSummary.isPresent()) {
            return; // If summary already exists, do not create a new one
        }

        // Fetch today's weather data for the specified city
        WeatherData weatherData = weatherDataRepository.findByCityAndDate(city, today).orElse(null);

        if (weatherData == null) {
            // No weather data available for today; handle this scenario if needed
            return;
        }

        // Get the list of temperatures from the weatherData
        List<Double> temperatures = getTemperatureList(weatherData.getTemperatures());

        // Calculate min, max, and average temperature
        double minTemp = temperatures.stream().min(Double::compare).orElse(0.0);
        double maxTemp = temperatures.stream().max(Double::compare).orElse(0.0);
        double avgTemp = temperatures.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Create a new WeatherSummary object
        WeatherSummary weatherSummary = new WeatherSummary();
        weatherSummary.setCity(city);
        weatherSummary.setDate(today);
        weatherSummary.setMinTemp(minTemp);
        weatherSummary.setMaxTemp(maxTemp);
        weatherSummary.setAvgTemp(avgTemp);
        weatherSummary.setWeatherCondition(weatherData.getWeatherCondition());
        // Save the summary to the database
        weatherSummaryRepository.save(weatherSummary);
    }
    private List<Double> getTemperatureList(String temperatures) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (temperatures != null && !temperatures.isEmpty()) {
            return objectMapper.readValue(temperatures, List.class);
        }
        return new ArrayList<>();
    }
}
