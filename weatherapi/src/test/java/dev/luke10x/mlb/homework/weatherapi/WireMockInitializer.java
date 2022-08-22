package dev.luke10x.mlb.homework.weatherapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;
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

    public static WireMock recreateWiremockByUrl(String urlString) throws MalformedURLException {
        var url = new URL(urlString);
        return new WireMock(url.getProtocol(), url.getHost(), url.getPort());
    }
}