package com.technology.daniel.javachallenge;

import com.technology.daniel.javachallenge.show_runner.ShowRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaChallengeApplication implements CommandLineRunner {
	@Autowired
	ShowRunner showRunner;

	private static Logger LOG = LoggerFactory
			.getLogger(JavaChallengeApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(JavaChallengeApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) {
		showRunner.run(args);
	}


}
