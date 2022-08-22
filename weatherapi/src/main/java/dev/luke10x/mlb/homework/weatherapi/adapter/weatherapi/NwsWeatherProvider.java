package dev.luke10x.mlb.homework.weatherapi.adapter.weatherapi;

import dev.luke10x.generated.openapi.client.nws.ApiException;
import dev.luke10x.generated.openapi.client.nws.api.DefaultApi;
import dev.luke10x.generated.openapi.client.nws.model.GridpointForecastGeoJson;
import dev.luke10x.generated.openapi.client.nws.model.GridpointForecastPeriod;
import dev.luke10x.generated.openapi.client.nws.model.GridpointForecastUnits;
import dev.luke10x.generated.openapi.client.nws.model.PointGeoJson;
import dev.luke10x.mlb.homework.weatherapi.domain.exception.UnexpectedPayloadException;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.model.Weather;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@Log
public class NwsWeatherProvider implements WeatherProvider {

    @Autowired
    private DefaultApi nwsWeatherApi;

    @Override
    public Weather getWeatherForVenueAt(Venue venue, OffsetDateTime gameStartsAt) {
        var coordinates = formatCoordinates(venue.longitude(), venue.latitude());

        // Fetch 2 endpoints on National Weather Service
        var point = fetchPoint(coordinates);
        var hourlyForecasts = fetchHourlyForecasts(point);

        var period = hourlyForecasts.getProperties().getPeriods().stream()
                // TODO Optimize it with .skip()
                .peek(p -> log.info("🔁 trying period: "+p.getStartTime()+"-"+p.getEndTime()))
                .filter(p -> !gameStartsAt.isBefore(p.getStartTime()) && gameStartsAt.isBefore(p.getEndTime()))
                .findFirst()
                .orElseThrow(() -> new UnexpectedPayloadException(
                        "Game does not start within a period of any forecasts"));

        return assembleWeather(period);
    }

    private String formatCoordinates(double longitude, double latitude) {
        var df = new DecimalFormat("###.####");
        return df.format(latitude) + "," + df.format(longitude);
    }

    private PointGeoJson fetchPoint(String coordinates) {
        try {
            return nwsWeatherApi.point(coordinates);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GridpointForecastGeoJson fetchHourlyForecasts(PointGeoJson point) {
        var officeId = point.getProperties().getCwa();
        var gridX = point.getProperties().getGridX();
        var gridY = point.getProperties().getGridY();

        try {
            return nwsWeatherApi.gridpointForecastHourly(officeId, gridX, gridY, List.of(), GridpointForecastUnits.US);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Weather assembleWeather(GridpointForecastPeriod period) {
        var windSpeed = period.getWindSpeed().getValue();
        var windDirection = period.getWindDirection().getValue();

        var summary = "Wind " + windDirection + " at " + windSpeed;
        return new Weather(summary);
    }
}
