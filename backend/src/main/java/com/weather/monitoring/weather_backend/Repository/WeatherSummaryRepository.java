package com.weather.monitoring.weather_backend.Repository;


import com.weather.monitoring.weather_backend.Model.WeatherSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@Repository
//public interface WeatherSummaryRepository extends JpaRepository<WeatherSummary, Long> {
//}
//@Repository
//public interface WeatherSummaryRepository extends JpaRepository<WeatherSummary, Long> {
//    Optional<WeatherSummary> findByCityAndDate(String city, LocalDate date);
//
//    List<WeatherSummary> findByCityAndDateBetween(String city, LocalDate pastWeek, LocalDate today);
//}
@Repository
public interface WeatherSummaryRepository extends JpaRepository<WeatherSummary, Long> {
    Optional<WeatherSummary> findByCityAndDate(String city, LocalDate date);
}
