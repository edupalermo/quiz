package org.palermo.quiz.repository.mapper;

import org.palermo.quiz.domain.Room;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomRowMapper {

    public RowMapper<Room> get() {
        return get("");
    }

    public RowMapper<Room> get(String prefix) {
        return (resultSet, i) -> Room.builder()
                .identifier(resultSet.getString(prefix + "IDENTIFIER"))
                .creation(resultSet.getTimestamp(prefix + "CREATION").toLocalDateTime())
                .lastActivity(resultSet.getTimestamp(prefix + "LAST_ACTIVITY").toLocalDateTime())
                .build();
    }
}
