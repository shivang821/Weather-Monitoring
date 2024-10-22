package com.weather.monitoring.weather_backend.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDate date;
    private double temperature;
    private double feelsLike;
    private String weatherCondition;
    @Column(columnDefinition = "TEXT")
    private String temperatures; // Store temperature readings as a JSON string
    // getters and setters
    public String getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(String temperatures) {
        this.temperatures = temperatures;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public LocalDate getTimestamp() {
        return date;
    }

    public void setTimestamp(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", temperature=" + temperature +
                ", feelsLike=" + feelsLike +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", temperatures='" + temperatures + '\'' +
                '}';
    }
}
