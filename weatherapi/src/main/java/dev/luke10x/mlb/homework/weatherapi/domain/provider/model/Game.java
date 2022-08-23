package dev.luke10x.mlb.homework.weatherapi.domain.provider.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record Game(
        String id,
        String venueId,
        String homeTeamName,
        String awayTeamName,
        LocalDate officialDate,
        OffsetDateTime startsAt
) {}
