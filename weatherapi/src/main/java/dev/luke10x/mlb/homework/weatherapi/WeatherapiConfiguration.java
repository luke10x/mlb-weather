package dev.luke10x.mlb.homework.weatherapi;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@Log
public class WeatherapiConfiguration {
    @Value("${mlbStatsApi.url}")
    String statsUrl;

    @Value("${nwsWeatherApi.url}")
    String weatherUrl;

    @Bean
    Clock clock() {
        log.info("⏰ Configuring to use system clock");
        log.info("📌 statsUrl = "+statsUrl);
        log.info("📌 weatherUrl = "+weatherUrl);
        return Clock.systemUTC();
    }
}
