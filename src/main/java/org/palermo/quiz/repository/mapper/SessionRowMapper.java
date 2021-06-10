package org.palermo.quiz.repository.mapper;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.domain.Session;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SessionRowMapper {

    private final RoomRowMapper roomRowMapper;
    private final AccountRowMapper accountRowMapper;

    public RowMapper<Session> get(String sessionPrefix, String accountPrefix, String roomPrefix) {
        return (resultSet, i) -> Session.builder()
                .room(roomRowMapper.get(roomPrefix).mapRow(resultSet, i))
                .account(accountRowMapper.get(accountPrefix).mapRow(resultSet, i))
                .points(resultSet.getInt(sessionPrefix + "POINTS"))
                .creation(resultSet.getTimestamp(sessionPrefix + "CREATION").toLocalDateTime())
                .build();
    }
}
