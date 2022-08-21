package dev.luke10x.mlb.homework.weatherapi.domain.handler;

import dev.luke10x.mlb.homework.weatherapi.domain.model.BallparkConditions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BallparkConditionsQueryHandlerTest {
    @Autowired BallparkConditionsQueryHandler underTest;

    @Test
    public void itWorks() {
        var result = underTest.getCurrentBallparkConditions("3289");

        assertThat(result.venueName())
                .isEqualTo("Citi Field, Flushing");
        assertThat(result.weatherConditions())
                .isEqualTo("Wind SE at 12 mph");
    }
}
