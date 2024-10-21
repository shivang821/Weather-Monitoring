package com.weather.monitoring.weather_backend.Repository;

import com.weather.monitoring.weather_backend.Model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//import java.util.Optional;

//@Repository
//public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
//    List<WeatherData> findByCityAndTimestampBetween(String city, LocalDateTime start, LocalDateTime end);
//}
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByCityAndDate(String city, LocalDate date);

    List<WeatherData> findByDateAndCityIn(LocalDate today, List<String> cities);
}