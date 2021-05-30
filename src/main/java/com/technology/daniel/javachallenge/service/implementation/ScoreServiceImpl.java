package com.technology.daniel.javachallenge.service.implementation;

import com.technology.daniel.javachallenge.domain.model.Frame;
import com.technology.daniel.javachallenge.service.ScoreService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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

    @Override
    public Map<String, List<Frame>> generateScore(Map<String, List<Frame>> frameMap) {
        Iterator iterator = frameMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)iterator.next();
            List<Frame> playerFrames = (List<Frame>) mapElement.getValue();
            calculateFramesScore(playerFrames);
        }
        return frameMap;
    }

    private void calculateFramesScore(List<Frame> frames) {
        Integer framePoints = 0;
        for(int i=0; i < frames.size(); i++) {
            Frame currentFrame = frames.get(i);
            currentFrame.setIsSpare(isFrameSpare(currentFrame));
            currentFrame.setIsStrike(isFrameStrike(currentFrame));
            framePoints += currentFrame.getPinFallsFirstRound();

            if (currentFrame.getIsStrike() && !currentFrame.getIsLastFrame()) {
                Frame nxtFrame = frames.get(i+1);
                framePoints += nxtFrame.getPinFallsFirstRound();
                if (isFrameStrike(nxtFrame) && !nxtFrame.getIsLastFrame()) {
                    nxtFrame = frames.get(i+2);
                    framePoints += nxtFrame.getPinFallsFirstRound();
                } else {
                    framePoints += nxtFrame.getPinFallsSecondRound();
                }

            }
            else if (currentFrame.getIsSpare() && !currentFrame.getIsLastFrame()) {
                framePoints += currentFrame.getPinFallsSecondRound();
                Frame nxtFrame = frames.get(i+1);
                framePoints += nxtFrame.getPinFallsFirstRound();
            }
            else if(currentFrame.getIsLastFrame()) {
                framePoints += currentFrame.getPinFallsSecondRound();
                if (currentFrame.getIsStrike() || currentFrame.getIsSpare()) {
                    framePoints += currentFrame.getPinFallsThirdRound();
                }
            }
            else {
                framePoints += currentFrame.getPinFallsSecondRound();
            }
            currentFrame.setScore(framePoints);
        }
    }

    private Boolean isFrameStrike(Frame frame) {
        if (frame.getPinFallsFirstRound() == 10) {
            return true;
        }
        return false;
    }

    private Boolean isFrameSpare(Frame frame) {
        if (frame.getPinFallsFirstRound() != 10 && frame.getPinFallsFirstRound() + frame.getPinFallsSecondRound() == 10) {
            return true;
        }
        return false;
    }
}
