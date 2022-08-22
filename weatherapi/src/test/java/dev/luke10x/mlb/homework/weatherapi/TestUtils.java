package dev.luke10x.mlb.homework.weatherapi;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.OffsetDateTime;

public class TestUtils {
    public static Clock fixedOn(String text) {
        return (Clock) InstantSource.fixed(Instant.from(OffsetDateTime.parse(text)));
    }
}
