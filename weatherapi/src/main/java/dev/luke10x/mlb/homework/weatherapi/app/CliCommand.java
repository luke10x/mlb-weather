package dev.luke10x.mlb.homework.weatherapi.app;
import dev.luke10x.mlb.homework.weatherapi.domain.handler.BallparkConditionsQueryHandler;
import dev.luke10x.mlb.homework.weatherapi.domain.handler.GameForecastQueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.*;

import java.time.LocalDate;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Component
@Command(name = "mailCommand")
public class CliCommand implements Callable<Integer> {

    @Autowired private GameForecastQueryHandler gameForecastQueryHandlerer;
    @Autowired private BallparkConditionsQueryHandler ballparkConditionsQueryHandler;

    @Option(names = "--venue", description = "Venue in which you want to see weather conditions")
    String venue;

    @Option(names = "--team", description = "Your team")
    String team;

    @Option(names = "--date", description = "Date of the game")
    LocalDate date;

    public Integer call() throws Exception {
        if (venue != null && (team == null && date == null)) {
            var result = ballparkConditionsQueryHandler.getCurrentBallparkConditions(venue);
            System.out.println("");
            System.out.println("🏟 "+result.venue().name());
            System.out.println("🌤 "+result.weather().summary());
            System.out.println("");
            return 0;
        }
        if (team != null && date != null) {
            var result = gameForecastQueryHandlerer.getWeatherForecastsForMyTeamGames(team, date);
            System.out.println("");
            System.out.println("Games in total: "+result.size());
            result.stream().peek(r -> {
                System.out.println("💚 "+r.game().awayTeamName()+" @ "+r.game().homeTeamName() );
                System.out.println("🏁 Starts "+r.venue().name()+" on "+r.game().startsAt());
                System.out.println("🌤 "+r.weather().summary());
                System.out.println("");
            }).collect(Collectors.toList());

            return 0;
        }
        return 1;
    }
}