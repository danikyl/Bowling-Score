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
        System.out.println("Frame\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10");
    }

    private void printPlayerScore(List<Frame> playerFrames) {
        System.out.println(playerFrames.get(0).getPlayerName());
        System.out.print("Pinfalls\t");
        for (Frame frame : playerFrames) {
            System.out.print(convertRoundsToScoreFormat(frame));
        }
        System.out.println();
        System.out.print("Score\t");
        for (Frame frame : playerFrames) {
            System.out.print(convertFrameScore(frame));
        }
        System.out.println();
    }

    private String convertRoundsToScoreFormat(Frame frame) {
        StringBuilder scoreFormat = new StringBuilder();
        if (!frame.getIsLastFrame()) {
            if (frame.isFrameStrike()) {
                scoreFormat.append("\tX\t");
            } else if (frame.isFrameSpare()) {
                scoreFormat.append(frame.getPinFallsFirstRound() + "\t/\t");
            } else {
                scoreFormat.append(frame.getPinFallsFirstRound() + "\t" + frame.getPinFallsSecondRound() + "\t");
            }
        } else {
            scoreFormat.append(convertNumberToBowlingSymbol(frame.getPinFallsFirstRound()) + "\t" + convertNumberToBowlingSymbol(frame.getPinFallsSecondRound()));
            if (frame.isFrameSpare() || frame.isFrameStrike()) {
                scoreFormat.append("\t" + convertNumberToBowlingSymbol(frame.getPinFallsThirdRound()));
            }
        }
        return scoreFormat.toString();

    }

    private String convertFrameScore(Frame frame) {
        return frame.getScore() + "\t\t";
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
            framePoints += validatePinFalls(currentFrame.getPinFallsFirstRoundInteger());
            //STRIKE CASE
            if (currentFrame.isFrameStrike() && !currentFrame.getIsLastFrame()) {
                Frame nxtFrame = frames.get(i + 1);
                framePoints += validatePinFalls(nxtFrame.getPinFallsFirstRoundInteger());
                if (nxtFrame.isFrameStrike() && !nxtFrame.getIsLastFrame()) {
                    nxtFrame = frames.get(i + 2);
                    framePoints += validatePinFalls(nxtFrame.getPinFallsFirstRoundInteger());
                } else {
                    framePoints += validatePinFalls(nxtFrame.getPinFallsSecondRoundInteger());
                }

            }
            //SPARE CASE
            else if (currentFrame.isFrameSpare() && !currentFrame.getIsLastFrame()) {
                framePoints += currentFrame.getPinFallsSecondRoundInteger();
                Frame nxtFrame = frames.get(i + 1);
                framePoints += validatePinFalls(nxtFrame.getPinFallsFirstRoundInteger());
            }
            //LAST FRAME CASE
            else if (currentFrame.getIsLastFrame()) {
                framePoints += validatePinFalls(currentFrame.getPinFallsSecondRoundInteger());
                if (currentFrame.isFrameStrike() || currentFrame.isFrameSpare()) {
                    framePoints += validatePinFalls(currentFrame.getPinFallsThirdRoundInteger());
                }
            }
            //ORDINARY CASE
            else {
                framePoints += validatePinFalls(currentFrame.getPinFallsSecondRoundInteger());
            }
            currentFrame.setScore(framePoints);
        }
    }

    private Integer validatePinFalls(Integer pinFalls) {
        if (pinFalls < 0 || pinFalls > 10) {
            throw new FileWrongFormatException("Provided play is not valid.");
        }
        return pinFalls;
    }
}
