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
                String firstPinFalls = getNextRecordFromFile(reader, newFrame, true);
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
                    String secondPinFalls = getNextRecordFromFile(reader, newFrame, false);
                    newFrame.setPinFallsSecondRound(secondPinFalls);
                }

                //CASE OF PLAYER'S LAST FRAME
                if (playersFrameCount.get(newFrame.getPlayerName()) == 10) {
                    newFrame.setIsLastFrame(true);
                    if (newFrame.isFrameSpare() || newFrame.isFrameStrike()) {
                        //Get third play of frame
                        String thirdPinFalls = getNextRecordFromFile(reader, newFrame, false);
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

    private String getNextRecordFromFile(Scanner reader, Frame newFrame, Boolean setPlayerName) {
        String row;
        String[] ballPlayInfo;
        row = reader.nextLine();
        ballPlayInfo = row.split("\t");
        if (setPlayerName) {
            newFrame.setPlayerName(ballPlayInfo[0]);
        }
        return ballPlayInfo[1];
    }
}
