package org.palermo.quiz.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ShowMessageEvent {

    private final String type = EventType.SHOW_MESSAGE;
    private final String roomIdentifier;
    private final String message;
}
