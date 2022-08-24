package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import dev.luke10x.mlb.homework.weatherapi.domain.exception.DateOutOfRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
                    "112", LocalDate.now().plusDays(3)
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
}