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
            if (matchFrames.size() < 1) throw new FileWrongFormatException("File is empty");
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
                String firstPinFalls = getNextRecordFromFile(reader, newFrame);
                newFrame.setPinFallsFirstRound(firstPinFalls);

                //Check if player is already present on counter map
                if (playersFrameCount.get(newFrame.getPlayerName()) != null) {
                    playersFrameCount.put(newFrame.getPlayerName(), playersFrameCount.get(newFrame.getPlayerName()) + 1);
                } else {
                    playersFrameCount.put(newFrame.getPlayerName(), 1);
                }


                //STRIKE CASE
                if (!firstPinFalls.equals("10") || playersFrameCount.get(newFrame.getPlayerName()) == 10) {
                    //Get second play of frame
                    String secondPinFalls = getNextRecordFromFile(reader, newFrame);
                    newFrame.setPinFallsSecondRound(secondPinFalls);
                }

                //CASE OF PLAYER'S LAST FRAME
                if (playersFrameCount.get(newFrame.getPlayerName()) == 10) {
                    newFrame.setIsLastFrame(true);
                    if (isFrameSpare(newFrame) || isFrameStrike(newFrame)) {
                        //Get third play of frame
                        String thirdPinFalls = getNextRecordFromFile(reader, newFrame);
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

    private String getNextRecordFromFile(Scanner reader, Frame newFrame) {
        String row;
        String[] ballPlayInfo;
        row = reader.nextLine();
        ballPlayInfo = row.split("\t");
        validatePlayerName(newFrame, ballPlayInfo[0]);
        return validatePinFalls(ballPlayInfo[1]);
    }

    private String validatePinFalls(String pinFalls) {
        if (!pinFalls.equals("F") && (Integer.parseInt(pinFalls) < 0 || Integer.parseInt(pinFalls) > 10)) {
            throw new FileWrongFormatException("Provided play is not valid.");
        }
        return pinFalls;
    }

    private Boolean isFrameSpare(Frame frame) {
        if (Integer.parseInt(frame.getPinFallsFirstRound()) + Integer.parseInt(frame.getPinFallsSecondRound()) == 10) {
            return true;
        }
        return false;
    }

    private Boolean isFrameStrike(Frame frame) {
        if (frame.getPinFallsFirstRound().equals("10")) {
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
