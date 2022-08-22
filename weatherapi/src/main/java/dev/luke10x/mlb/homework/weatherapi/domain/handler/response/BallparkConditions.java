package dev.luke10x.mlb.homework.weatherapi.domain.handler.response;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Weather;

public record BallparkConditions(Venue venue, Weather weather) {}
