package com.weather.monitoring.weather_backend.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_summary")
public class WeatherSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private double minTemp;
    private double maxTemp;
    private double avgTemp;
    private String weatherCondition;
    private double windSpeed;
    private LocalDate date;

    // Getters and Setters

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

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

//@Entity
//@Table(name = "daily_summary")
//public class WeatherSummary {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String city;
//    private LocalDate date;
//    private double averageTemperature;
//    private double maxTemperature;
//    private double minTemperature;
//    private String dominantWeatherCondition;
//
//    // getters and setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public double getAverageTemperature() {
//        return averageTemperature;
//    }
//
//    public void setAverageTemperature(double averageTemperature) {
//        this.averageTemperature = averageTemperature;
//    }
//
//    public double getMaxTemperature() {
//        return maxTemperature;
//    }
//
//    public void setMaxTemperature(double maxTemperature) {
//        this.maxTemperature = maxTemperature;
//    }
//
//    public double getMinTemperature() {
//        return minTemperature;
//    }
//
//    public void setMinTemperature(double minTemperature) {
//        this.minTemperature = minTemperature;
//    }
//
//    public String getDominantWeatherCondition() {
//        return dominantWeatherCondition;
//    }
//
//    public void setDominantWeatherCondition(String dominantWeatherCondition) {
//        this.dominantWeatherCondition = dominantWeatherCondition;
//    }

//}

