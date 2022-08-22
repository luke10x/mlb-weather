package dev.luke10x.mlb.homework.weatherapi.domain.provider;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Weather;

import java.time.OffsetDateTime;

public interface WeatherProvider {
    @Deprecated
    Weather getCurrentWeather(Venue venue);
    Weather getWeatherForVenueAt(Venue venue, OffsetDateTime timeWhenGameStarts);
}
