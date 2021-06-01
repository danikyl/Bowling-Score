package com.technology.daniel.javachallenge.service;

import com.technology.daniel.javachallenge.domain.model.Frame;

import java.util.List;

public interface FileParser {
    List<Frame> loadMatchFramesFromFile(String filename);
}
