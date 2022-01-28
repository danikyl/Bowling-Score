package com.technology.daniel.bowlingscore.service;

import com.technology.daniel.bowlingscore.domain.model.Frame;

import java.util.List;

public interface FileParser {
    List<Frame> loadMatchFramesFromFile(String filename);
}
