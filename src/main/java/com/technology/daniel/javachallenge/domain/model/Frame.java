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
    private String playerName;

    private String pinFallsFirstRound;

    private String pinFallsSecondRound;

    private String pinFallsThirdRound;

    private Boolean isStrike;

    private Boolean isSpare;

    private Integer score;

    private Boolean isLastFrame;

    private Integer faultOnRound;

    public Integer getPinFallsFirstRoundInteger() {
        if (pinFallsFirstRound.equals("F")) return 0;
        return Integer.parseInt(pinFallsFirstRound);
    }

    public Integer getPinFallsSecondRoundInteger() {
        if (pinFallsSecondRound.equals("F")) return 0;
        return Integer.parseInt(pinFallsSecondRound);
    }

    public Integer getPinFallsThirdRoundInteger() {
        if (pinFallsThirdRound.equals("F")) return 0;
        return Integer.parseInt(pinFallsThirdRound);
    }
}
