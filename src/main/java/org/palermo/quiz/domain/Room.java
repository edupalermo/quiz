package org.palermo.quiz.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Room {

    private final String identifier;
    private final LocalDateTime creation;
    private final LocalDateTime lastActivity;
}
