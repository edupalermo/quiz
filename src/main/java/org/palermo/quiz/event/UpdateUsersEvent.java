package org.palermo.quiz.event;

import lombok.Builder;
import lombok.Getter;
import org.palermo.quiz.event.model.User;

import java.util.List;

@Getter
@Builder
public final class UpdateUsersEvent {

    private final String type = EventType.UPDATE_USERS;
    private final String roomIdentifier;
    private final List<User> users;
}
