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

    @Override
    public String toString() {
        return "WeatherSummary{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", avgTemp=" + avgTemp +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", windSpeed=" + windSpeed +
                ", date=" + date +
                '}';
    }
}

