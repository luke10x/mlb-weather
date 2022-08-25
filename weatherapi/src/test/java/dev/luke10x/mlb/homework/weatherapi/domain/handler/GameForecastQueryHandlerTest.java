package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.stubbing.StubImport;
import com.github.tomakehurst.wiremock.stubbing.StubImportBuilder;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import dev.luke10x.mlb.homework.weatherapi.TestUtils;
import dev.luke10x.mlb.homework.weatherapi.WireMockInitializer;
import dev.luke10x.mlb.homework.weatherapi.domain.exception.DateOutOfRange;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.stubbing.StubImport.*;
import static com.github.tomakehurst.wiremock.stubbing.StubMapping.buildFrom;
import static dev.luke10x.mlb.homework.weatherapi.WireMockInitializer.recreateWiremockByUrl;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest

class GameForecastQueryHandlerTest {

    @Nested
    @Tag("Smoke") class SmokeTest {
        @Autowired
        GameForecastQueryHandler handler;

        @Test
        @DisplayName("--team 112 --date 2022-04-07 - Chicago Cubs")
        public void canCheckCubs() {
            var result = handler.getWeatherForecastsForMyTeamGames(
                    "112", LocalDate.parse("2022-08-25")
            );

            assertThat(result.size()).isNotNull();
        }

        @Test
        @DisplayName("--team 121 --date 2022-04-07 - New York Mets")
        public void canCheckMets() {
            var result = handler.getWeatherForecastsForMyTeamGames(
                    "121", LocalDate.now().plusDays(3)
            );

            assertThat(result.size()).isNotNull();
        }

        @Test
        @DisplayName("--team 137 --date 2022-04-07 - San Francisco Giants")
        public void canCheckGiants() {
            var result = handler.getWeatherForecastsForMyTeamGames(
                    "137", LocalDate.now().plusDays(3)
            );

            assertThat(result.size()).isNotNull();
        }
    }

    @DisplayName("Unit test")
    @Nested @Tag("Unit") class UnitTest {

        @Autowired
        GameForecastQueryHandler handler;

        @Test @DisplayName("Shows error when passed day in the past")
        void failsIfInThePast() {

            var threeDaysAgo =  LocalDate.now().minusDays(3);

            assertThatThrownBy(() -> {
                handler.getWeatherForecastsForMyTeamGames("121", threeDaysAgo);
            })
                    .isInstanceOf(DateOutOfRange.class)
                    .hasMessageContaining("Game date cannot be in the past");
        }

        @Test @DisplayName("Shows error when passed day to far in the future")
        void failsIfTooFarAhead() {

            var tenDaysAhead =  LocalDate.now().plusDays(10);
            assertThatThrownBy(() -> {
                handler.getWeatherForecastsForMyTeamGames("121", tenDaysAhead);
            })
                    .isInstanceOf(DateOutOfRange.class)
                    .hasMessageContaining("Game date cannot be more than one wee in the future");
        }
    }

    @ContextConfiguration(initializers = WireMockInitializer.class)
    @SpringBootTest
    @DisplayName("Integration test")
    @Nested @Tag("Integration") class IntegrationTest {

        @Value("${mlbStatsApi.url}")
        private String statsUrl;

        @Value("${nwsWeatherApi.url}")
        private String weatherUrl;
        @Autowired
        ResourceLoader resourceLoader;
        @Autowired
        GameForecastQueryHandler handler;

        @TestConfiguration
        public static class WebClientConfiguration {
            @Bean
            @Primary
            public Clock clock() {
                return TestUtils.fixedOn("2022-08-24T02:01-04:00");
            }
        }

        @AfterEach
        void cleanUp() throws MalformedURLException {
            recreateWiremockByUrl(statsUrl).removeMappings();
            recreateWiremockByUrl(weatherUrl).removeMappings();
        }

        @Test
        void forecastForTomorrowFlow() throws IOException {

            var wm = recreateWiremockByUrl(statsUrl);
            wm.loadMappingsFrom(
                    resourceLoader
                            .getResource("classpath:wiremock/2022-08-24T11_11/stats")
                            .getFile()
            );
            var wm2 = recreateWiremockByUrl(weatherUrl);
            wm2.loadMappingsFrom(
                    resourceLoader
                            .getResource("classpath:wiremock/2022-08-24T11_11/weather")
                            .getFile()
            );

            var result = handler.getWeatherForecastsForMyTeamGames("112", LocalDate.parse("2022-08-25"));

            assertThat(result.size()).isEqualTo(1);
        }
    }
}