package dev.luke10x.mlb.homework.weatherapi.domain.provider;

import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Game;

import java.time.LocalDate;
import java.util.List;

public interface ScheduledGamesProvider {

    List<Game> getGamesScheduledFor(String teamId, LocalDate day);
}
