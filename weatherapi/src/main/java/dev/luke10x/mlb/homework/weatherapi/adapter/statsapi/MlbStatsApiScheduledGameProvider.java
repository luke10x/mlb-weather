package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.ScheduledGamesProvider;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class MlbStatsApiScheduledGameProvider implements ScheduledGamesProvider {

    @Autowired
    MlbStatsApiHttpClient httpClient;

    @Override
    public List<Game> getGamesScheduledFor(String teamId, LocalDate day) {

        // To make sure we get all games assigned to that date
        // regardless on what is local time at the user
        var startDate = day.minusDays(1).toString();
        var endDate = day.plusDays(1).toString();

        MlbStatsApiScheduleResponse response = httpClient.getSchedule(teamId, startDate, endDate);

        return response.dates().stream()
                .map(d -> d.games())
                .flatMap(g -> g.stream())
                .filter(g -> g.officialDate().equals(day))
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(
                                (a, b) -> (int)(a.gamePk() - b.gamePk())
                        )
                )).stream()
                .map(g -> new Game(
                        String.valueOf(g.gamePk()),
                        String.valueOf(g.venue().id()),
                        g.teams().home().team().name(),
                        g.teams().away().team().name(),
                        g.officialDate(),
                        g.gameDate()
                )).collect(Collectors.toList());
    }
}
