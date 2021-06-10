package org.palermo.quiz.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Question {

    private final String fullPath;
    private final String identifier;
    private final LocalDateTime creation;
}
