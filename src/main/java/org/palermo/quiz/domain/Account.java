package org.palermo.quiz.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Account {

    private final String email;
    private final LocalDateTime creation;
}
