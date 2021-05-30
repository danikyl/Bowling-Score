package com.technology.daniel.javachallenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Frame {
    private Player player;

    private Integer pinFallsFirstRound;

    private Integer pinFallsSecondRound;

    private Boolean isStrike;

    private Boolean isSpare;
}
