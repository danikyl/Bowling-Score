package com.technology.daniel.bowlingscore.service;

import com.technology.daniel.bowlingscore.domain.model.Frame;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    Map<String, List<Frame>> divideMatchFramesPerPlayer(List<Frame> matchFrames);

    Map<String, List<Frame>> generateScore(Map<String, List<Frame>> frameMap);

    void printMatchScore(Map<String, List<Frame>> frameMap);
}
