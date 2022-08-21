package dev.luke10x.mlb.homework.weatherapi.domain.provider;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;

public interface VenueProvider {
    Venue getVenue(String venueId);
}
