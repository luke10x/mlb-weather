package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import dev.luke10x.mlb.homework.weatherapi.domain.model.BallparkConditions;
import dev.luke10x.mlb.homework.weatherapi.domain.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.model.Weather;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.VenueProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BallparkConditionsQueryHandler {
    @Autowired
    private VenueProvider venueProvider;

//    @Autowired
//    private WeatherProvider weatherProvider;

    public BallparkConditions getCurrentBallparkConditions(String venueId) {
        Venue venue = venueProvider.getVenue(venueId);
//        Weather weather = weatherProvider.getCurrentWeather(venue);

        return new BallparkConditions(
                venue.name(),
//                weather.summary()
                "weather.summary()"
        );
    }
}
