package dev.luke10x.mlb.homework.weatherapi.domain.handler.response;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Game;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Weather;

public record GameForecast(Game game, Venue venue, Weather weather) {}

