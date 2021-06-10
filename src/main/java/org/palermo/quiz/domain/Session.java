package org.palermo.quiz.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Session {

    private final Room room;
    private final Account account;
    private final int points;
    private final LocalDateTime creation;
}
