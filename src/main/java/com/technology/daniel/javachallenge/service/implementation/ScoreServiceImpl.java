package com.technology.daniel.javachallenge.service.implementation;

import com.technology.daniel.javachallenge.domain.model.Frame;
import com.technology.daniel.javachallenge.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Override
    public Map<String, List<Frame>> divideMatchFramesPerPlayer(List<Frame> matchFrames) {
        Map<String, List<Frame>> framesMap = matchFrames.stream().collect(Collectors.groupingBy(frame -> frame.getPlayerName()));
        ;
        return framesMap;
    }
}
