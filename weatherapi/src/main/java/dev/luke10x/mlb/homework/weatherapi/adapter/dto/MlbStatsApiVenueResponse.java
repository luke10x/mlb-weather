package dev.luke10x.mlb.homework.weatherapi.adapter.dto;

import java.util.List;

public record MlbStatsApiVenueResponse(List<Venue> venues) {
    public record DefaultCoordinates(
            double longitude,
            double latitude
    ) {
    }

    public record Location(
            String address1,
            String city,
            DefaultCoordinates defaultCoordinates
    ) {
    }

    public record Venue(
            String name,
            Location location
    ) {
    }
}