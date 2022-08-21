package dev.luke10x.mlb.homework.weatherapi.domain.provider;

import dev.luke10x.mlb.homework.weatherapi.domain.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.model.Weather;

public interface WeatherProvider {
    Weather getCurrentWeather(Venue venue);
}
