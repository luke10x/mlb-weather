package dev.luke10x.mlb.homework.weatherapi;

import dev.luke10x.mlb.homework.weatherapi.app.CliCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import picocli.CommandLine;

@SpringBootApplication
@EnableFeignClients
public class WeatherapiApplication  implements CommandLineRunner, ExitCodeGenerator {
	@Autowired
	private CommandLine.IFactory factory;

	@Autowired
	private CliCommand mailCommand;
	private int exitCode;

	@Override
	public void run(String... args) {
		// let picocli parse command line args and run the business logic
		exitCode = new CommandLine(mailCommand, factory).execute(args);
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}

	public static void main(String[] args) {
		// let Spring instantiate and inject dependencies
		System.exit(SpringApplication.exit(SpringApplication.run(WeatherapiApplication.class, args)));
	}
}
