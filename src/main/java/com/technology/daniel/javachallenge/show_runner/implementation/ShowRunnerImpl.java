package com.technology.daniel.javachallenge.show_runner.implementation;

import com.technology.daniel.javachallenge.JavaChallengeApplication;
import com.technology.daniel.javachallenge.parser.FileParser;
import com.technology.daniel.javachallenge.show_runner.ShowRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowRunnerImpl implements ShowRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(JavaChallengeApplication.class);

    @Autowired
    FileParser fileParser;

    @Override
    public void run(String... args) {
        String filename;
        LOG.info("EXECUTING : command line runner");

        if (args.length < 1) {
            LOG.error("Please provide a file name as argument");
            return;
        }
        filename = args[0];
        LOG.info("File name: " + filename);
        LOG.info(fileParser.getFileContent(filename));
    }
}
