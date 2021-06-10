package org.palermo.quiz.listener.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Info {
    private final String roomIdentifier;
    private final String userEmail;
}
