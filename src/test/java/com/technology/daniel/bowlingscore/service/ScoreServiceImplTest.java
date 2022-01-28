package com.technology.daniel.bowlingscore.service;

import com.technology.daniel.bowlingscore.domain.model.Frame;
import com.technology.daniel.bowlingscore.service.implementation.ScoreServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreServiceImplTest {

    @Test
    public void testCalculateOrdinaryMatchPointsFramesPerPlayer() {
        ScoreServiceImpl scoreServiceImpl = new ScoreServiceImpl();
        Map<String, List<Frame>> map = generateOrdinaryMockFrameMap();
        scoreServiceImpl.generateScore(map);
        assertEquals(map.get("John").get(9).getScore() == 151 && map.get("Jeff").get(9).getScore() == 167, true);
    }

    private Map<String, List<Frame>> generateOrdinaryMockFrameMap() {
        Map<String, List<Frame>> frameMap = new HashMap<>();

        List<Frame> frameList1 = new ArrayList<>();
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("3").pinFallsSecondRound("7").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("6").pinFallsSecondRound("3").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("8").pinFallsSecondRound("1").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("9").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("7").pinFallsSecondRound("3").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("4").pinFallsSecondRound("4").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").pinFallsSecondRound("9").pinFallsThirdRound("0").isLastFrame(true).build());

        frameMap.put("John", frameList1);

        List<Frame> frameList2 = new ArrayList<>();
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("7").pinFallsSecondRound("3").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("9").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("0").pinFallsSecondRound("8").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("8").pinFallsSecondRound("2").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("6").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").pinFallsSecondRound("8").pinFallsThirdRound("1").isLastFrame(true).build());

        frameMap.put("Jeff", frameList2);
        return frameMap;
    }

    @Test
    public void testCalculateAllZeroAndAllFaultMatchPointsFramesPerPlayer() {
        ScoreServiceImpl scoreServiceImpl = new ScoreServiceImpl();
        Map<String, List<Frame>> map = generateAllZeroMockFrameMap();
        scoreServiceImpl.generateScore(map);
        assertEquals(map.get("John").get(9).getScore() == 0 && map.get("Jeff").get(9).getScore() == 0, true);
    }

    private Map<String, List<Frame>> generateAllZeroMockFrameMap() {
        Map<String, List<Frame>> frameMap = new HashMap<>();

        List<Frame> frameList1 = new ArrayList<>();
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("0").pinFallsSecondRound("0").isLastFrame(true).build());

        frameMap.put("John", frameList1);

        List<Frame> frameList2 = new ArrayList<>();
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("F").pinFallsSecondRound("F").isLastFrame(true).build());

        frameMap.put("Jeff", frameList2);
        return frameMap;
    }

    @Test
    public void testCalculateAllStrikeMatchPointsFramesPerPlayer() {
        ScoreServiceImpl scoreServiceImpl = new ScoreServiceImpl();
        Map<String, List<Frame>> map = generateAllStrikeMockFrameMap();
        scoreServiceImpl.generateScore(map);
        assertEquals(map.get("John").get(9).getScore() == 300 && map.get("Jeff").get(9).getScore() == 300, true);
    }

    private Map<String, List<Frame>> generateAllStrikeMockFrameMap() {
        Map<String, List<Frame>> frameMap = new HashMap<>();

        List<Frame> frameList1 = new ArrayList<>();
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList1.add(Frame.builder().playerName("John").pinFallsFirstRound("10").pinFallsSecondRound("10").pinFallsThirdRound("10").isLastFrame(true).build());

        frameMap.put("John", frameList1);

        List<Frame> frameList2 = new ArrayList<>();
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").isLastFrame(false).build());
        frameList2.add(Frame.builder().playerName("Jeff").pinFallsFirstRound("10").pinFallsSecondRound("10").pinFallsThirdRound("10").isLastFrame(true).build());

        frameMap.put("Jeff", frameList2);
        return frameMap;
    }
}
