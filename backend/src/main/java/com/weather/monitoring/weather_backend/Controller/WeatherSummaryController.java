package com.weather.monitoring.weather_backend.Controller;

import com.weather.monitoring.weather_backend.Model.WeatherSummary;
//import com.weather.monitoring.weather_backend.Service.WeatherService;
import com.weather.monitoring.weather_backend.Service.WeatherSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//public class WeatherSummaryController {
//
//    @Autowired
//    private WeatherService weatherService;
//
//    @GetMapping("/city/{city}")
//    public List<WeatherSummary> getWeatherSummary(@PathVariable String city) {
//        // Get past 7 days + today’s summary
//        return weatherService.getWeatherSummaryForPastDays(city, 7);
//    }
//}

@RestController
public class WeatherSummaryController {

    @Autowired
    private WeatherSummaryService weatherSummaryService;

    @GetMapping("/summary/{city}")
    public WeatherSummary getWeatherSummary(@PathVariable String city) throws Exception {
        return weatherSummaryService.getWeatherSummary(city);
    }
    @GetMapping("/past-summary/{city}")
    public List<WeatherSummary> GetPastWeatherSummary(@PathVariable String city) throws Exception{
        return weatherSummaryService.getPastWeekWeatherSummary(city);
    }
}