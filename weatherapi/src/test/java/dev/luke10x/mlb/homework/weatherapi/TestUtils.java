package dev.luke10x.mlb.homework.weatherapi;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.OffsetDateTime;

@Log
public class TestUtils {
    public static Clock fixedOn(String text) {
        log.info("⏱ Configuring to use fixed clock");
        return (Clock) InstantSource.fixed(Instant.from(OffsetDateTime.parse(text)));
    }
}
