package dev.luke10x.mlb.homework.weatherapi.adapter;

import dev.luke10x.mlb.homework.weatherapi.domain.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.model.Weather;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;

public class NwsWeatherProvider implements WeatherProvider {
    @Override
    public Weather getCurrentWeather(Venue venue) {
        return null;
    }
}
