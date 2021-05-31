package com.technology.daniel.javachallenge.service.implementation;

import com.technology.daniel.javachallenge.domain.model.Frame;
import com.technology.daniel.javachallenge.exception.FileWrongFormatException;
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
            Map.Entry mapElement = (Map.Entry) iterator.next();
            List<Frame> playerFrames = (List<Frame>) mapElement.getValue();
            calculateFramesScore(playerFrames);
        }
        return frameMap;
    }

    @Override
    public void printMatchScore(Map<String, List<Frame>> frameMap) {
        printMatchHeader();
        Iterator iterator = frameMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            List<Frame> playerFrames = (List<Frame>) mapElement.getValue();
            printPlayerScore(playerFrames);
        }
    }

    private void printMatchHeader() {
        System.out.println("Frame      1     2     3     4     5     6     7     8     9     10");
    }

    private void printPlayerScore(List<Frame> playerFrames) {
        System.out.println(playerFrames.get(0).getPlayerName());
        System.out.print("Pinfalls  ");
        for (Frame frame : playerFrames) {
            System.out.print(convertRoundsToScoreFormat(frame));
        }
        System.out.println();
    }

    private String convertRoundsToScoreFormat(Frame frame) {
        StringBuilder scoreFormat = new StringBuilder();
        if (!frame.getIsLastFrame()) {
            if (frame.getIsStrike()) {
                scoreFormat.append("  X   ");
            } else if (frame.getIsSpare()) {
                scoreFormat.append(frame.getPinFallsFirstRound() + " /   ");
            } else {
                scoreFormat.append(frame.getPinFallsFirstRound() + " " + frame.getPinFallsSecondRound() + "   ");
            }
        } else {
            scoreFormat.append(convertNumberToBowlingSymbol(frame.getPinFallsFirstRound()) + " " + convertNumberToBowlingSymbol(frame.getPinFallsSecondRound()));
            if (frame.getIsSpare() || frame.getIsStrike()) {
                scoreFormat.append(" " + convertNumberToBowlingSymbol(frame.getPinFallsThirdRound()));
            }
        }
        return scoreFormat.toString();

    }

    private String convertNumberToBowlingSymbol(String number) {
        if (number.equals("10")) return "X";
        else return number;
    }


    private void calculateFramesScore(List<Frame> frames) {
        if (frames.size() > 10) throw new FileWrongFormatException("There can not be more than 10 frames per player");
        Integer framePoints = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame currentFrame = frames.get(i);
            currentFrame.setIsSpare(isFrameSpare(currentFrame));
            currentFrame.setIsStrike(isFrameStrike(currentFrame));
            framePoints += currentFrame.getPinFallsFirstRoundInteger();

            if (currentFrame.getIsStrike() && !currentFrame.getIsLastFrame()) {
                Frame nxtFrame = frames.get(i + 1);
                framePoints += nxtFrame.getPinFallsFirstRoundInteger();
                if (isFrameStrike(nxtFrame) && !nxtFrame.getIsLastFrame()) {
                    nxtFrame = frames.get(i + 2);
                    framePoints += nxtFrame.getPinFallsFirstRoundInteger();
                } else {
                    framePoints += nxtFrame.getPinFallsSecondRoundInteger();
                }

            } else if (currentFrame.getIsSpare() && !currentFrame.getIsLastFrame()) {
                framePoints += currentFrame.getPinFallsSecondRoundInteger();
                Frame nxtFrame = frames.get(i + 1);
                framePoints += nxtFrame.getPinFallsFirstRoundInteger();
            } else if (currentFrame.getIsLastFrame()) {
                framePoints += currentFrame.getPinFallsSecondRoundInteger();
                if (currentFrame.getIsStrike() || currentFrame.getIsSpare()) {
                    framePoints += currentFrame.getPinFallsThirdRoundInteger();
                }
            } else {
                framePoints += currentFrame.getPinFallsSecondRoundInteger();
            }
            currentFrame.setScore(framePoints);
        }
    }

    private Boolean isFrameStrike(Frame frame) {
        if (frame.getPinFallsFirstRoundInteger() == 10) {
            return true;
        }
        return false;
    }

    private Boolean isFrameSpare(Frame frame) {
        if (frame.getPinFallsFirstRoundInteger() != 10 && frame.getPinFallsFirstRoundInteger() + frame.getPinFallsSecondRoundInteger() == 10) {
            return true;
        }
        return false;
    }
}
