package com.technology.daniel.bowlingscore.service;

import com.technology.daniel.bowlingscore.domain.model.Frame;
import com.technology.daniel.bowlingscore.exception.FileWrongFormatException;
import com.technology.daniel.bowlingscore.service.implementation.FileParserImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileParserImplTest {

    @Test
    public void testLoadPerfectMatchFramesFromFile() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        List<Frame> framesToValidate = fileParserImpl.loadMatchFramesFromFile("src/test/resources/positive/perfect.txt");
        assertEquals((verifyIfAllAreStrike(framesToValidate) && framesToValidate.size() == 10), true);

    }

    @Test
    public void testLoadAllZeroMatchFramesFromFile() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        List<Frame> framesToValidate = fileParserImpl.loadMatchFramesFromFile("src/test/resources/positive/all-rows-zero.txt");
        assertEquals((verifyIfAllAreZero(framesToValidate) && framesToValidate.size() == 10), true);

    }

    @Test
    public void testLoadAllFaultMatchFramesFromFile() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        List<Frame> framesToValidate = fileParserImpl.loadMatchFramesFromFile("src/test/resources/positive/all-rows-F.txt");
        assertEquals((verifyIfAllAreFault(framesToValidate) && framesToValidate.size() == 10), true);

    }

    @Test
    public void testLoadMatchWithTwoPlayersFramesFromFile() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        List<Frame> framesToValidate = fileParserImpl.loadMatchFramesFromFile("src/test/resources/positive/scores.txt");
        assertEquals(framesToValidate.size() == 20, true);

    }

    @Test
    public void testLoadEmptyMatchFramesFromFileShouldThrowException() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        assertThrows(FileWrongFormatException.class, () -> {
            fileParserImpl.loadMatchFramesFromFile("src/test/resources/negative/empty.txt");
        });

    }

    @Test
    public void testLoadExtraScoreMatchFramesFromFileShouldThrowException() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        assertThrows(FileWrongFormatException.class, () -> {
            fileParserImpl.loadMatchFramesFromFile("src/test/resources/negative/extra-score.txt");
        });

    }

    @Test
    public void testLoadInvalidTextMatchFramesFromFileShouldThrowException() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        assertThrows(FileWrongFormatException.class, () -> {
            fileParserImpl.loadMatchFramesFromFile("src/test/resources/negative/free-text.txt");
        });

    }

    @Test
    public void testLoadInvalidScoreMatchFramesFromFileShouldThrowException() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        assertThrows(FileWrongFormatException.class, () -> {
            fileParserImpl.loadMatchFramesFromFile("src/test/resources/negative/invalid-score.txt");
        });

    }

    @Test
    public void testLoadNegativeScoreMatchFramesFromFileShouldThrowException() {
        FileParserImpl fileParserImpl = new FileParserImpl();
        assertThrows(FileWrongFormatException.class, () -> {
            fileParserImpl.loadMatchFramesFromFile("src/test/resources/negative/negative.txt");
        });

    }

    private Boolean verifyIfAllAreStrike(List<Frame> frames) {
        Boolean allStrike = frames.stream().allMatch(frame -> frame.isFrameStrike().equals(true));
        return allStrike;
    }

    private Boolean verifyIfAllAreZero(List<Frame> frames) {
        Boolean allFirstRoundZero = frames.stream().allMatch(frame -> (frame.getPinFallsFirstRound()).equals("0"));
        Boolean allSecondRoundZero = frames.stream().allMatch(frame -> (frame.getPinFallsSecondRound()).equals("0"));
        return allFirstRoundZero && allSecondRoundZero;
    }

    private Boolean verifyIfAllAreFault(List<Frame> frames) {
        Boolean allFirstRoundFault = frames.stream().allMatch(frame -> (frame.getPinFallsFirstRound()).equals("F"));
        Boolean allSecondRoundFault = frames.stream().allMatch(frame -> (frame.getPinFallsSecondRound()).equals("F"));
        return allFirstRoundFault && allSecondRoundFault;
    }
}
