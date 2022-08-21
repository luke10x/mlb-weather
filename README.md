# API fetching weather at ballparks and upcoming games 

This software is written following a request from MLB,
in order to prepare for a job interview for a Java Develper
position at MLB.

## Functionality 

This application serves two use cases:

- Shows current temperature and conditions at a ballpark (by `venue` ID);
- Shows forecasted temperature and conditions of the upcoming game
  (by `team` ID and `date` of the game).

## Solution

## Types of tests

For testing we use JUnit with Surefire 
We have different types of tests in this application:

### Unit tests

They verify that the logic in our domain handlers is correct.
Typically, they have boundary of our business domain mocked.
These tests are not supposed to interact with anything outside the application,
and they are fast to execute.

### Integration tests

Integration tests ensure that:

- domain handlers
- and data provider adapters

can work together.
Running this suite **does not** use any

- production API or
- production database

instead they use Wiremock and H2.
This suite is supposed to cover most crucial user stories.

### Contract tests

Contract tests ensure that changes in our codebase
do not break integration with 3rd parties.

These tests only check how data provider adapters work
with different API responses.
Narrow test scope focused on the boundary of our system
should also serve as live  documentation
for integrations with 3rd parties.
These tests use Wiremock heavily.

### Smoke tests

Smoke tests run use Production configuration
Smoke tests pass if there are no errors and
results are in acceptable range.
(Typically smoke tests don't test exact values,
as the state in Production constantly changes).

The purpose of these tests is to indicate which
functionality is broken on production.

Smoke tests suit are more considered to be monitoring tool,
rather than testing.

And they are supposed to be performed on regular basis.
(As opposed others are performed mostly after some 
changes in our codebase).

### Retrospective


....................
.......
.........
BallparkConditions

GameForecast  


TEAMS: getSchedule()
  GEO, PLACE: getVenues(VENUE)
    OFFICE: getOfficeBy(GEO)
      TEMP: getForecastsBy(OFFICE)



https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-maven-plugin/README.md