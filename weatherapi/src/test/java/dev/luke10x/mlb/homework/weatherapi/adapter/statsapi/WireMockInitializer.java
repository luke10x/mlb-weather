package dev.luke10x.mlb.homework.weatherapi.adapter.statsapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        WireMockServer statsWireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
        WireMockServer weatherWireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());

        statsWireMockServer.start();
        weatherWireMockServer.start();
        TestPropertyValues
                .of(Map.of("mlbStatsApi.url", statsWireMockServer.baseUrl(),
                "nwsWeatherApi.url", weatherWireMockServer.baseUrl()))
                .applyTo(applicationContext);
    }
}