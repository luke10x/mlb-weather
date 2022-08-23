package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi.venue;

import dev.luke10x.mlb.homework.weatherapi.adapter.statsapi.MlbStatsApiHttpClient;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.VenueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MlbStatsVenueProvider implements VenueProvider {

    @Autowired
    MlbStatsApiHttpClient httpClient;

    @Override
    public Venue getVenue(String venueId) {
        // Fetch venue details from MBL Stats endpoint
        var response = httpClient.getVenue(venueId);

        var location = response.venues().get(0).location();
        var venueName = assembleVenueName(location);
        var longitude = location.defaultCoordinates().longitude();
        var latitude = location.defaultCoordinates().latitude();

        return new Venue(venueId, venueName, longitude, latitude);
    }

    private String assembleVenueName(MlbStatsApiVenueResponse.Location location) {
        var address1 = location.address1();
        var city = location.city();
        return address1 + ", " + city;
    }
}
