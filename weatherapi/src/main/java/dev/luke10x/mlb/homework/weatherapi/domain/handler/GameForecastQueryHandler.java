package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import dev.luke10x.mlb.homework.weatherapi.domain.handler.response.GameForecast;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.ScheduledGamesProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.VenueProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Game;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GameForecastQueryHandler {
    @Autowired
    private VenueProvider venueProvider;

    @Autowired
    private ScheduledGamesProvider scheduledGamesProvider;

    @Autowired
    private WeatherProvider weatherProvider;

    public List<GameForecast> getCurrentBallparkConditions(String teamId, LocalDate day) {

        List<Game> games = scheduledGamesProvider.getGamesScheduledFor(teamId, day);

        return games.stream().map(g -> {
            var homeVenue = venueProvider.getVenue(g.homeVenueId());
            var weather = weatherProvider.getWeatherForVenueAt(homeVenue, g.startsAt());

            return new GameForecast(g, homeVenue, weather);
        }).collect(Collectors.toList());
    }
}
