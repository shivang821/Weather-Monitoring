package com.weather.monitoring.weather_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherMonitoringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherMonitoringBackendApplication.class, args);
	}

}
