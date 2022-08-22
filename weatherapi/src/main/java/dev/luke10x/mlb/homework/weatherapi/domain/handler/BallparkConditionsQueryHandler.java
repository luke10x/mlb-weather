package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import dev.luke10x.mlb.homework.weatherapi.domain.handler.response.BallparkConditions;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Weather;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.VenueProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.OffsetDateTime;

@Component
public class BallparkConditionsQueryHandler {
    @Autowired
    private VenueProvider venueProvider;

    @Autowired
    private WeatherProvider weatherProvider;

    @Autowired
    Clock clock;

    public BallparkConditions getCurrentBallparkConditions(String venueId) {
        Venue venue = venueProvider.getVenue(venueId);
//        Weather weather = weatherProvider.getCurrentWeather(venue);

        var weather = weatherProvider.getWeatherForVenueAt(venue, OffsetDateTime.now(clock));

        return new BallparkConditions(
                venue.name(),
                weather.summary()
        );
    }
}
