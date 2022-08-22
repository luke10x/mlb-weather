package dev.luke10x.mlb.homework.weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class WeatherapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(WeatherapiApplication.class, args);
	}
}
