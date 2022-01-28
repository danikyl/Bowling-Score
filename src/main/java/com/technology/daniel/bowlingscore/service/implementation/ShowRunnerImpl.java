package com.technology.daniel.bowlingscore.service.implementation;

import com.technology.daniel.bowlingscore.BowlingScoreApplication;
import com.technology.daniel.bowlingscore.domain.model.Frame;
import com.technology.daniel.bowlingscore.exception.FileWrongFormatException;
import com.technology.daniel.bowlingscore.exception.NotFoundException;
import com.technology.daniel.bowlingscore.service.FileParser;
import com.technology.daniel.bowlingscore.service.ScoreService;
import com.technology.daniel.bowlingscore.service.ShowRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShowRunnerImpl implements ShowRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(BowlingScoreApplication.class);

    @Autowired
    FileParser fileParser;

    @Autowired
    ScoreService scoreService;

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
            List<Frame> frames = fileParser.loadMatchFramesFromFile(filename);

            Map<String, List<Frame>> framesByPlayer = scoreService.divideMatchFramesPerPlayer(frames);

            scoreService.generateScore(framesByPlayer);

            scoreService.printMatchScore(framesByPlayer);


        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
        } catch (FileWrongFormatException e) {
            LOG.error(e.getMessage());
        }
    }
}
