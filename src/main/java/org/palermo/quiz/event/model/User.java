package org.palermo.quiz.event.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {

    private final String nickname;
    private final int points;
}
