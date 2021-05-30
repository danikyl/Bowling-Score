package com.technology.daniel.javachallenge.service;

import com.technology.daniel.javachallenge.domain.model.Frame;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    Map<String, List<Frame>> divideMatchFramesPerPlayer(List<Frame> matchFrames);
}
