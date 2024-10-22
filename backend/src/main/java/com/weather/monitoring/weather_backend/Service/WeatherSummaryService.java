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


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@Service
public class WeatherSummaryService {
    @Autowired
    private WeatherDataService weatherDataService;
    @Autowired
    private WeatherSummaryRepository weatherSummaryRepository;

    @Autowired
    private WeatherDataRepository weatherDataRepository;
    @Value("${openweather.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherSummary getWeatherSummary(String city) throws Exception {
        LocalDate today = LocalDate.now();
        generateDailySummary(city);
        // Check if the summary for today already exists
        WeatherSummary weatherSummary = weatherSummaryRepository.findByCityAndDate(city, today).orElse(null);
        return weatherSummary;
    }
    public List<WeatherSummary> getPastWeekWeatherSummary(String city) throws Exception {
        List<WeatherSummary> summaries = new ArrayList<>();
        LocalDate today = LocalDate.now();
        generateDailySummary(city);
        WeatherSummary todaySummary=weatherSummaryRepository.findByCityAndDate(city, today).orElse(null);
        summaries.add(todaySummary);
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

        // Fetch today's weather data for the specified city
        WeatherData weatherData = weatherDataRepository.findByCityAndDate(city, today).orElse(null);

        if (weatherData == null) {
            // No weather data available for today; fetch it
            weatherData = weatherDataService.getWeatherData(city);
            weatherDataRepository.save(weatherData);
        }

        // Get the list of temperatures from the weatherData
        List<Double> temperatures = getTemperatureList(weatherData.getTemperatures());

//        double minTemp = root.get("main").get("temp_min").asDouble() - 273.15;
        double minTemp = temperatures.stream().min(Double::compare).orElse(0.0);
        double maxTemp = temperatures.stream().max(Double::compare).orElse(0.0);
        double avgTemp = temperatures.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Check if today's summary already exists
        Optional<WeatherSummary> existingSummaryOptional = weatherSummaryRepository.findByCityAndDate(city, today);

        WeatherSummary weatherSummary;

        if (existingSummaryOptional.isPresent()) {
            // Update the existing summary
            weatherSummary = existingSummaryOptional.get();
            weatherSummary.setMinTemp(minTemp);
            weatherSummary.setMaxTemp(maxTemp);
            weatherSummary.setAvgTemp(avgTemp);
            weatherSummary.setWeatherCondition(weatherData.getWeatherCondition());
        } else {
            // Create a new WeatherSummary object
            weatherSummary = new WeatherSummary();
            weatherSummary.setCity(city);
            weatherSummary.setDate(today);
            weatherSummary.setMinTemp(minTemp);
            weatherSummary.setMaxTemp(maxTemp);
            weatherSummary.setAvgTemp(avgTemp);
            weatherSummary.setWeatherCondition(weatherData.getWeatherCondition());
        }

        // Save the updated summary to the database
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