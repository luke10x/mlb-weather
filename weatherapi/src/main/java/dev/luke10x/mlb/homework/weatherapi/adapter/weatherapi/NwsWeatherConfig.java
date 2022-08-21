package dev.luke10x.mlb.homework.weatherapi.adapter.weatherapi;

import dev.luke10x.generated.openapi.client.nws.ApiClient;
import dev.luke10x.generated.openapi.client.nws.api.DefaultApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class NwsWeatherConfig {

    @Value("${nwsWeatherApi.url}")
    private String baseUrl;

    @Bean
    public DefaultApi nwsWeatherApi() {
        var client = new ApiClient();
        client.setBasePath(baseUrl);
        URL url = null;
        try {
            url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        client.setScheme(url.getProtocol());
        client.setHost(url.getHost());
        client.setPort(url.getPort());
        client.setBasePath(url.getPath());

        return new DefaultApi(client);
    }
}
