package com.technology.daniel.javachallenge.service.implementation;

import com.technology.daniel.javachallenge.domain.model.Frame;
import com.technology.daniel.javachallenge.exception.FileWrongFormatException;
import com.technology.daniel.javachallenge.exception.NotFoundException;
import com.technology.daniel.javachallenge.service.FileParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class FileParserImpl implements FileParser {

    @Override
    public List<Frame> loadMatchFramesFromFile(String filename) {
        try {
            File fileToRead = new File(filename);
            Scanner myReader = new Scanner(fileToRead);
            List<Frame> matchFrames = getFrames(myReader);
            myReader.close();
            return matchFrames;
        } catch (FileNotFoundException e) {
            throw new NotFoundException("File name not found: " + filename);
        }
    }

    private List<Frame> getFrames(Scanner reader) {
        try {
            List<Frame> matchFrames = new ArrayList<>();
            Map<String, Integer> playersFrameCount = new HashMap<>();
            while (reader.hasNextLine()) {
                Frame newFrame = Frame.builder().isLastFrame(false).build();


                //Get first play of frame
                Integer firstPinFalls = getNextRecordFromFile(reader, newFrame);
                newFrame.setPinFallsFirstRound(firstPinFalls);

                //Check if player is already present on counter map
                if (playersFrameCount.get(newFrame.getPlayerName()) != null) {
                    playersFrameCount.put(newFrame.getPlayerName(), playersFrameCount.get(newFrame.getPlayerName()) + 1);
                } else {
                    playersFrameCount.put(newFrame.getPlayerName(), 1);
                }


                //STRIKE CASE
                if (firstPinFalls != 10 || playersFrameCount.get(newFrame.getPlayerName()) == 10) {
                    //Get second play of frame
                    Integer secondPinFalls = getNextRecordFromFile(reader, newFrame);
                    newFrame.setPinFallsSecondRound(secondPinFalls);
                }

                //CASE OF PLAYER'S LAST FRAME
                if (playersFrameCount.get(newFrame.getPlayerName()) == 10) {
                    newFrame.setIsLastFrame(true);
                    if (isFrameSpare(newFrame) || isFrameStrike(newFrame)) {
                        //Get third play of frame
                        Integer thirdPinFalls = getNextRecordFromFile(reader, newFrame);
                        newFrame.setPinFallsThirdRound(thirdPinFalls);
                    }
                }

                matchFrames.add(newFrame);
            }

            return matchFrames;

        } catch (Exception e) {
            throw new FileWrongFormatException("File format is not supported.");
        }
    }

    private Integer getNextRecordFromFile(Scanner reader, Frame newFrame) {
        String row;
        String[] ballPlayInfo;
        row = reader.nextLine();
        ballPlayInfo = row.split("\t");
        validatePlayerName(newFrame, ballPlayInfo[0]);
        Integer pinFalls = convertAndValidatePinFalls(ballPlayInfo[1]);
        return pinFalls;
    }

    private Integer convertAndValidatePinFalls(String pinFallsString) {
        if (pinFallsString.equals("F")) pinFallsString = "0";
        Integer pinFallsInteger = Integer.parseInt(pinFallsString);
        if (pinFallsInteger < 0 || pinFallsInteger > 10) {
            throw new FileWrongFormatException("Provided play is not valid.");
        }
        return pinFallsInteger;
    }

    private Boolean isFrameSpare(Frame frame) {
        if (frame.getPinFallsFirstRound() + frame.getPinFallsSecondRound() == 10) {
            return true;
        }
        return false;
    }

    private Boolean isFrameStrike(Frame frame) {
        if (frame.getPinFallsFirstRound() == 10) {
            return true;
        }
        return false;
    }

    private void validatePlayerName(Frame frame, String playerName) {
        if (frame.getPlayerName() == null) {
            frame.setPlayerName(playerName);
        } else if (!frame.getPlayerName().equals(playerName))
            throw new FileWrongFormatException("Player name of first and second play doesn't match");
    }
}
