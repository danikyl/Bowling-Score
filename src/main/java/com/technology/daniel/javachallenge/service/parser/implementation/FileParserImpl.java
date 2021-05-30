package com.technology.daniel.javachallenge.service.parser.implementation;

import com.technology.daniel.javachallenge.domain.model.Frame;
import com.technology.daniel.javachallenge.domain.model.Player;
import com.technology.daniel.javachallenge.exception.FileWrongFormatException;
import com.technology.daniel.javachallenge.exception.NotFoundException;
import com.technology.daniel.javachallenge.service.parser.FileParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class FileParserImpl implements FileParser {

    @Override
    public ArrayList<Frame> loadMatchFramesFromFile(String filename) {
        try {
            File fileToRead = new File(filename);
            Scanner myReader = new Scanner(fileToRead);
            ArrayList<Frame> matchFrames = getFrames(myReader);
            myReader.close();
            return matchFrames;
        } catch (FileNotFoundException e) {
            throw new NotFoundException("File name not found: " + filename);
        }
    }

    private ArrayList<Frame> getFrames(Scanner reader) {
        try {
            ArrayList<Frame> matchFrames = new ArrayList<>();
            while (reader.hasNextLine()) {
                Frame newFrame = Frame.builder().build();

                String row = reader.nextLine();
                String[] ballPlayInfo = row.split("\t");

                Player player = Player.builder().name(ballPlayInfo[0]).build();
                newFrame.setPlayer(player);

                //Get first play of frame
                Integer firstPinFalls = convertAndValidatePinFalls(ballPlayInfo[1]);
                newFrame.setPinFallsFirstRound(firstPinFalls);
                //STRIKE CASE
                if (firstPinFalls == 10) {
                    newFrame.setIsStrike(true);
                    newFrame.setIsSpare(false);
                    newFrame.setPinFallsSecondRound(0);
                } else {
                    newFrame.setIsStrike(false);
                    //Get second play of frame
                    row = reader.nextLine();
                    ballPlayInfo = row.split("\t");
                    validatePlayerNameSecondPlay(newFrame, ballPlayInfo[0]);
                    Integer secondPinFalls = convertAndValidatePinFalls(ballPlayInfo[1]);
                    newFrame.setPinFallsSecondRound(secondPinFalls);
                    newFrame.setIsSpare(isFrameSpare(newFrame));
                }

                matchFrames.add(newFrame);
            }

            return matchFrames;

        } catch (Exception e) {
            throw new FileWrongFormatException("File format is not supported.");
        }
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
        if(frame.getPinFallsFirstRound() + frame.getPinFallsSecondRound() > 10) {
            throw new FileWrongFormatException("Provided frame is invalid. Total pin falls can not be greater than 10");
        }
        if (frame.getPinFallsFirstRound() + frame.getPinFallsSecondRound() == 10) {
            return true;
        }
        return false;
    }
    private void validatePlayerNameSecondPlay(Frame frame, String secondPlayName) {
        if (!frame.getPlayer().getName().equals(secondPlayName)) throw new FileWrongFormatException("Player name of first and second play doesn't match");
    }
}
