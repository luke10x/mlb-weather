package dev.luke10x.mlb.homework.weatherapi.adapter.weatherapi;

import dev.luke10x.mlb.homework.weatherapi.TestUtils;
import dev.luke10x.mlb.homework.weatherapi.WireMockInitializer;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;

import java.net.MalformedURLException;
import java.time.Clock;
import java.time.OffsetDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static dev.luke10x.mlb.homework.weatherapi.WireMockInitializer.recreateWiremockByUrl;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("Contract")
@ContextConfiguration(initializers = WireMockInitializer.class)
@SpringBootTest
class NwsWeatherProviderTest {
    @TestConfiguration
    public static class WebClientConfiguration {
        @Bean @Primary
        public Clock clock() {
            return TestUtils.fixedOn("2022-08-21T22:01-04:00");
        }
    }

    @Value("${nwsWeatherApi.url}")
    private String weatherUrl;

    @Autowired
    NwsWeatherProvider provider;

    @Autowired
    Clock clock;

    @Test
    @DisplayName("Provider calls points and then gridpoints endpoints to get current weather")
    void providerContract() throws MalformedURLException {
        var wm = recreateWiremockByUrl(weatherUrl);
        wm.resetMappings();
        wm.register(get(urlMatching(".*/points/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("response/2022-08-21T22_01/weather_points_407575-738456.json"))
                .build());
        wm.register(get(urlMatching(".*/gridpoints/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("response/2022-08-21T22_01/weather_gridpoints_okx_3737_forecast_hourly.json"))
                .build());

        var venue = new Venue("3289", "Citi Field, Flushing", -73.84559155,40.75753012);

        var weather = provider.getWeatherForVenueAt(venue, OffsetDateTime.now(clock));

        wm.verifyThat(getRequestedFor(urlEqualTo("/points/40.7575%2C-73.8456"))
                .withHeader("User-Agent", matching("Java.*")));
        wm.verifyThat(getRequestedFor(urlEqualTo("/gridpoints/OKX/37,37/forecast/hourly?units=us"))
                .withHeader("User-Agent", matching("Java.*")));

        assertThat(weather.summary()).isEqualTo("75F, Slight Chance Rain Showers, wind SE at 6 mph");
    }
}