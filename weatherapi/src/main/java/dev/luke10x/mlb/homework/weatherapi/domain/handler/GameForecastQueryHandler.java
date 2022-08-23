package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import dev.luke10x.mlb.homework.weatherapi.domain.exception.DateOutOfRange;
import dev.luke10x.mlb.homework.weatherapi.domain.handler.response.GameForecast;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.ScheduledGamesProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.VenueProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameForecastQueryHandler {
    @Autowired
    private VenueProvider venueProvider;

    @Autowired
    private ScheduledGamesProvider scheduledGamesProvider;

    @Autowired
    private WeatherProvider weatherProvider;

    @Autowired
    Clock clock;

    public List<GameForecast> getWeatherForecastsForMyTeamGames(String teamId, LocalDate day) {
        var now = LocalDate.now(clock);
        if (day.isBefore(now)) {
            throw new DateOutOfRange("Game date cannot be in the past");
        }

        if (day.isAfter(now.plusDays(6))) {
            throw new DateOutOfRange("Game date cannot be more than one wee in the future");
        }

        // There should not be too many games per one day,
        // most of the time this is a list of one
        List<Game> games = scheduledGamesProvider.getGamesScheduledFor(teamId, day);

        return games.stream().map(g -> {
            var homeVenue = venueProvider.getVenue(g.venueId());
            var weather = weatherProvider.getWeatherForVenueAt(homeVenue, g.startsAt());

            return new GameForecast(g, homeVenue, weather);
        }).collect(Collectors.toList());
    }
}
