package dev.luke10x.mlb.homework.weatherapi.adapter;

import dev.luke10x.mlb.homework.weatherapi.adapter.dto.MlbStatsApiVenueResponse;
import dev.luke10x.mlb.homework.weatherapi.domain.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.VenueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MlbStatsVenueProvider implements VenueProvider {

    @Autowired
    MlbStatsApiHttpClient httpClient;

    @Override
    public Venue getVenue(String venueId) {

        var response = httpClient.getVenue(venueId);

        var address1 = response.venues().get(0).location().address1();
        var city = response.venues().get(0).location().city();
        var name = address1 + ", " + city;

        var longitude = response.venues().get(0).location().defaultCoordinates().longitude();
        var latitude = response.venues().get(0).location().defaultCoordinates().latitude();
        Venue venue = new Venue(venueId, name, longitude, latitude);
        return venue;
    }
}
