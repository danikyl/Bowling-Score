package com.technology.daniel.javachallenge.service.parser;

import com.technology.daniel.javachallenge.domain.model.Frame;

import java.util.List;
import java.util.Map;

public interface FileParser {
    Map<String, List<Frame>> loadMatchFramesFromFile(String filename);
}
