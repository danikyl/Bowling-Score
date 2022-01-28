package com.technology.daniel.bowlingscore.integration;

import com.technology.daniel.bowlingscore.domain.model.Frame;
import com.technology.daniel.bowlingscore.service.FileParser;
import com.technology.daniel.bowlingscore.service.ScoreService;
import com.technology.daniel.bowlingscore.service.implementation.FileParserImpl;
import com.technology.daniel.bowlingscore.service.implementation.ScoreServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadFileAndCalculateScoreIntegrationTest {
    @Test
    public void LoadFileAndCalculatePerfectScoreTest() {
        FileParser fileParser = new FileParserImpl();
        ScoreService scoreService = new ScoreServiceImpl();

        List<Frame> frames = fileParser.loadMatchFramesFromFile("src/test/resources/positive/perfect.txt");

        Map<String, List<Frame>> framesByPlayer = scoreService.divideMatchFramesPerPlayer(frames);

        scoreService.generateScore(framesByPlayer);

        assertEquals(framesByPlayer.get("Carl").get(9).getScore() == 300, true);
    }

    @Test
    public void LoadFileAndCalculateAllZeroScoreTest() {
        FileParser fileParser = new FileParserImpl();
        ScoreService scoreService = new ScoreServiceImpl();

        List<Frame> frames = fileParser.loadMatchFramesFromFile("src/test/resources/positive/all-rows-zero.txt");

        Map<String, List<Frame>> framesByPlayer = scoreService.divideMatchFramesPerPlayer(frames);

        scoreService.generateScore(framesByPlayer);

        assertEquals(framesByPlayer.get("Carl").get(9).getScore() == 0, true);
    }

    @Test
    public void LoadFileAndCalculateAllFaultScoreTest() {
        FileParser fileParser = new FileParserImpl();
        ScoreService scoreService = new ScoreServiceImpl();

        List<Frame> frames = fileParser.loadMatchFramesFromFile("src/test/resources/positive/all-rows-F.txt");

        Map<String, List<Frame>> framesByPlayer = scoreService.divideMatchFramesPerPlayer(frames);

        scoreService.generateScore(framesByPlayer);

        assertEquals(framesByPlayer.get("Carl").get(9).getScore() == 0, true);
    }

    @Test
    public void LoadFileAndCalculateOrdinaryScoreTest() {
        FileParser fileParser = new FileParserImpl();
        ScoreService scoreService = new ScoreServiceImpl();

        List<Frame> frames = fileParser.loadMatchFramesFromFile("src/test/resources/positive/scores.txt");

        Map<String, List<Frame>> framesByPlayer = scoreService.divideMatchFramesPerPlayer(frames);

        scoreService.generateScore(framesByPlayer);

        assertEquals(framesByPlayer.get("John").get(9).getScore() == 151 && framesByPlayer.get("Jeff").get(9).getScore() == 167, true);
    }

}
