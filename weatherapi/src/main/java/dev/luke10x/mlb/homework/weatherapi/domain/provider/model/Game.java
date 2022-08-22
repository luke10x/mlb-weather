package dev.luke10x.mlb.homework.weatherapi.domain.provider.model;

import java.time.OffsetDateTime;

public record Game(String homeVenueId, OffsetDateTime startsAt) {
}
