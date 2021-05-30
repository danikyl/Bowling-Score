package com.technology.daniel.javachallenge.show_runner.implementation;

import com.technology.daniel.javachallenge.JavaChallengeApplication;
import com.technology.daniel.javachallenge.domain.model.Frame;
import com.technology.daniel.javachallenge.exception.FileWrongFormatException;
import com.technology.daniel.javachallenge.exception.NotFoundException;
import com.technology.daniel.javachallenge.service.parser.FileParser;
import com.technology.daniel.javachallenge.show_runner.ShowRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
        try {
            /* *******************************************************************************************
             *******************************************************************************************
             * NEED TO HANDLE LAST FRAME FOR EACH PLAYER
             * NEED TO CHECK IF THE NUMBER OF FRAMES FOR EACH PLAYER IS CORRECT
             * *******************************************************************************************
             * *******************************************************************************************
             */
            Map<String, List<Frame>> frames = fileParser.loadMatchFramesFromFile(filename);
            System.out.println("TESTE");
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
        } catch (FileWrongFormatException e) {
            LOG.error(e.getMessage());
        }
    }
}
