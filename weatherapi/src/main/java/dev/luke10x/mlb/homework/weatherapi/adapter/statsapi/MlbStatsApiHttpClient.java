package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi;

import dev.luke10x.mlb.homework.weatherapi.adapter.statsapi.schedule.MlbStatsApiScheduleResponse;
import dev.luke10x.mlb.homework.weatherapi.adapter.statsapi.venue.MlbStatsApiVenueResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "httpClient", url = "${mlbStatsApi.url}")
public interface MlbStatsApiHttpClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/venues/{venueId}?hydrate=location")
    @Headers({
            "User-Agent: PostmanRuntime/7.29.2",
            "Accept: application/json"
    })
    MlbStatsApiVenueResponse getVenue(
            @PathVariable("venueId") String venueId
    );


    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/schedule?scheduleTypes=games&sportIds=1&teamIds={teamId}&startDate={startDate}&endDate={endDate}")
    @Headers({
            "User-Agent: PostmanRuntime/7.29.2",
            "Accept: application/json"
    })
    MlbStatsApiScheduleResponse getSchedule(
            @PathVariable("teamId") String teamId,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate
    );
}
