package dev.luke10x.mlb.homework.weatherapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class WeatherapiConfiguration {
    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
