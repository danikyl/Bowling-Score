package com.technology.daniel.javachallenge.parser.implementation;

import com.technology.daniel.javachallenge.JavaChallengeApplication;
import com.technology.daniel.javachallenge.parser.FileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
public class FileParserImpl implements FileParser {
    private static Logger LOG = LoggerFactory
            .getLogger(JavaChallengeApplication.class);

    @Override
    public String getFileContent(String filename) {
        StringBuilder fileContent = new StringBuilder();
        try {
            File fileToRead = new File(filename);
            Scanner myReader = new Scanner(fileToRead);
            while (myReader.hasNextLine()) {
                String row = myReader.nextLine();
                fileContent.append("\n" + row);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            LOG.error("File " + filename + " doesn't exists. Please provide a valid file name");
            return "";
        }
        return fileContent.toString().substring(1);
    }
}
