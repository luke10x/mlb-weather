package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("Contract")
@ContextConfiguration(initializers = WireMockInitializer.class)
@SpringBootTest
class MlbStatsVenueProviderTest {


    @Value("${mlbStatsApi.url}")
    private String statsUrl;

    @Autowired MlbStatsVenueProvider provider;
    @Test
    void some() throws MalformedURLException {
        URL url = new URL(statsUrl);
        WireMock wireMock1 = new WireMock(url.getProtocol(), url.getHost(), url.getPort());

        wireMock1.register(get(urlEqualTo("/api/v1/venues/3289?hydrate=location"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("venues_2389.json")).build());
        var venue = provider.getVenue("3289");


        assertThat(venue.venueId()).isEqualTo("3289");
        assertThat(venue.name()).isEqualTo("Citi Field, Flushing");
        assertThat(venue.longitude()).isEqualByComparingTo(-73.84559155);
        assertThat(venue.latitude()).isEqualByComparingTo(40.75753012);
    }
}