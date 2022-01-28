package com.technology.daniel.bowlingscore;

import com.technology.daniel.bowlingscore.service.ShowRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BowlingScoreApplication {
    @Autowired
    ShowRunner showRunner;

    private static Logger LOG = LoggerFactory
            .getLogger(BowlingScoreApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(BowlingScoreApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    public void run(String... args) {
        showRunner.run(args);
    }


}
