package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi;

import dev.luke10x.mlb.homework.weatherapi.WireMockInitializer;
import dev.luke10x.mlb.homework.weatherapi.adapter.statsapi.venue.MlbStatsVenueProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.net.MalformedURLException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static dev.luke10x.mlb.homework.weatherapi.WireMockInitializer.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("Contract")
@ContextConfiguration(initializers = WireMockInitializer.class)
@SpringBootTest
class MlbStatsVenueProviderTest {

    @Value("${mlbStatsApi.url}")
    private String statsUrl;

    @Autowired
    MlbStatsVenueProvider provider;
    @Test @DisplayName("Provider calls venues endpoint to get a venue with coordinates")
    void providerContract() throws MalformedURLException {
        var wm = recreateWiremockByUrl(statsUrl);
        wm.register(get(urlMatching(".*/venues/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("response/2022-08-21T22_01/stats_venues_2389.json")).build());

        var venue = provider.getVenue("3289");

        wm.verifyThat(getRequestedFor(urlEqualTo("/api/v1/venues/3289?hydrate=location"))
                .withHeader("User-Agent", matching("Java.*")));

        assertThat(venue.venueId()).isEqualTo("3289");
        assertThat(venue.name()).isEqualTo("Citi Field, Flushing");
        assertThat(venue.longitude()).isEqualByComparingTo(-73.84559155);
        assertThat(venue.latitude()).isEqualByComparingTo(40.75753012);
    }
}
