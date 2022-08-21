package dev.luke10x.mlb.homework.weatherapi.adapter;

import dev.luke10x.mlb.homework.weatherapi.domain.model.Venue;
import dev.luke10x.mlb.homework.weatherapi.domain.model.Weather;
import dev.luke10x.mlb.homework.weatherapi.domain.provider.WeatherProvider;
import org.openapitools.client.ApiClient;
//import org.openapitools.client.ApiException;
//import org.openapitools.client.ApiException;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.GridpointForecastUnits;
import org.openapitools.client.model.PointGeoJson;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class NwsWeatherProvider implements WeatherProvider {
    @Override
    public Weather getCurrentWeather(Venue venue) {
        var api = new DefaultApi();
        var client = new ApiClient();
//        api.setApiClient(client);
//        client.addDefaultHeader("Accept", "Application/ld+json");
//        client.selectHeaderContentType(new String[] {"Application/ld+json"});

        var longitude = venue.longitude();
        var latitude = venue.latitude();

        DecimalFormat df = new DecimalFormat("###.####");
        var formattedCoordinates = df.format(latitude) + "," + df.format(longitude);

        PointGeoJson point = null;
        try {
            point = api.point(formattedCoordinates);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (point == null) {
            throw new RuntimeException("⚠️ Cannot get pointgeo ");
        }

        var officeId = point.getProperties().getCwa();
        var gridX = point.getProperties().getGridX();
        var gridY = point.getProperties().getGridY();

        try {
            var gpForecast = api.gridpointForecastHourly(officeId, gridX, gridY, List.of(), GridpointForecastUnits.US);

            var windSpeed = gpForecast.getProperties().getPeriods().get(0).getWindSpeed().getValue();
            var windDirection = gpForecast.getProperties().getPeriods().get(0).getWindDirection().getValue();

            String summary = "Wind " + windDirection + " at " + windSpeed;
            return new Weather(summary);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
