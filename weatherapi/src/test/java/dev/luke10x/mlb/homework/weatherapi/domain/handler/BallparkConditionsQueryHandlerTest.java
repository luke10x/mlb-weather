package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BallparkConditionsQueryHandlerTest {

    private final String WEATHER_SUMMARY_PATTERN = "^Wind [ESWN]{1,3} at \\d* mph$";
    @Tag("Wiremock")
    @Nested @Tag("Smoke") class SmokeTest {
        @Autowired
        BallparkConditionsQueryHandler underTest;

        @Test @DisplayName("Check current weather at City Field on production")
        public void canCheckWeatherConditionsInCityField() {
            var result = underTest.getCurrentBallparkConditions("3289");

            assertThat(result.venue().name())
                    .isEqualTo("Citi Field, Flushing");
            assertThat(result.weather().summary())
                    .matches(WEATHER_SUMMARY_PATTERN);
        }

        @Test @DisplayName("Check current weather at Wrigley Field on production")
        public void canCheckWeatherConditionsInWrigleyField() {
            var result = underTest.getCurrentBallparkConditions("17");

            assertThat(result.venue().name())
                    .isEqualTo("1060 West Addison, Chicago");
            assertThat(result.weather().summary())
                    .matches(WEATHER_SUMMARY_PATTERN);
        }
    }

    @DisplayName("Unit test")
    @Nested @Tag("Unit") class UnitTest {
        @Test @DisplayName("It uses venue data from venue provider")
        void useVenueProvider() {
            assertThat(1 + 1).isEqualTo(2);
        }

        @Test @DisplayName("It uses weather data from provider for that venue")
        void useWeatherProvider() {
            assertThat(2 + 2).isEqualTo(4);
        }
    }
}
