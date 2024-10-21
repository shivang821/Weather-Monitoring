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

//@RestController
//public class WeatherController {
//    @Autowired
//    private WeatherService weatherService;
//
//    // Fetch real-time weather
//    @GetMapping("/")
//    public List<WeatherData> getWeatherDataOfAllCities(){
//        System.out.println("Called");
//        List<String> cities = Arrays.asList("Delhi","Jaipur", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");
//        List<WeatherData> data=new ArrayList<>();
//        cities.forEach((String city)->{
//            try {
//                WeatherData d=weatherService.fetchWeatherData((city));
//                data.add(d);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return data;
//    }
//    @GetMapping("/{city}")
//    public WeatherData getWeather(@PathVariable String city) throws JsonProcessingException {
//        return weatherService.fetchWeatherData(city);
//    }
//
//    // Fetch daily summary
//    @GetMapping("/summary/{city}")
//    public WeatherSummary getDailySummary(@PathVariable String city) {
//        return weatherService.calculateDailySummary(city);
//    }
//
//    // Check for temperature alert (for demo purposes)
//    @GetMapping("/alert/{city}/{threshold}")
//    public void checkAlert(@PathVariable String city, @PathVariable double threshold) throws JsonProcessingException {
//        WeatherData latestWeather = weatherService.fetchWeatherData(city);
//        weatherService.checkThresholds(latestWeather.getTemperature(), threshold);
//    }
//}
//@RestController
//public class WeatherController {
//
//    @Autowired
//    private WeatherService weatherService;
//
//    @GetMapping("/")
//    public List<WeatherData> getCurrentWeather() {
//        List<String>cities= Arrays.asList("Delhi","Mumbai","Banglaore","Chennai","Hyderabad","Kolkata");
//        List<WeatherData>result=new ArrayList<>();
//        cities.forEach((city)->{
//            result.add(weatherService.getWeatherData(city));
//        });
//        return result;
//    }
//}
@RestController
public class WeatherController {

    @Autowired
    private WeatherDataService weatherDataService;

    private static final List<String> CITIES = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

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
