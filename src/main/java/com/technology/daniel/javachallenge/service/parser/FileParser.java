package com.technology.daniel.javachallenge.service.parser;

import com.technology.daniel.javachallenge.domain.model.Frame;

import java.util.ArrayList;

public interface FileParser {
    ArrayList<Frame> loadMatchFramesFromFile(String filename);
}
