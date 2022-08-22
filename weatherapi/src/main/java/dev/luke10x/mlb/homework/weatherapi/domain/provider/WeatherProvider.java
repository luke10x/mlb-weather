package dev.luke10x.mlb.homework.weatherapi.domain.provider;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Weather;

import java.time.OffsetDateTime;

public interface WeatherProvider {
    Weather getWeatherForVenueAt(Venue venue, OffsetDateTime timeWhenGameStarts);
}
