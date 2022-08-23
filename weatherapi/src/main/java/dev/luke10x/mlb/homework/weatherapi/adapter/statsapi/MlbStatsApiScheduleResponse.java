package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
public record MlbStatsApiScheduleResponse(List<Date> dates) {
    public record Team (
            long id,
            String name
    ) {}

    public record TeamsItem (Team team) {}

    public record Teams (
            TeamsItem home,
            TeamsItem away
    ) {}

    public record Venue(
      long id,
      String name
    ) {}

    public record Game(
            long gamePk,
            OffsetDateTime gameDate,
            LocalDate officialDate,
            Teams teams,
            Venue venue
    ) {}

    public record Date(
            String date,
            List<Game> games
    ) {}

}