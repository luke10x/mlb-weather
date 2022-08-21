# API fetching weather at ballparks and upcoming games 

This software is written following a request from MLB,
in order to prepare for a job interview for a Java Develper
position at MLB.

## Functionality 

This application serves two use cases:

- Shows current temperature and conditions at a ballpark (by `venue` ID);
- Shows forecasted temperature and conditions of the upcoming game
  (by `team` ID and `date` of the game).

## Architecture

BallparkConditions

GameForecast  


TEAMS: getSchedule()
  GEO, PLACE: getVenues(VENUE)
    OFFICE: getOfficeBy(GEO)
      TEMP: getForecastsBy(OFFICE)



