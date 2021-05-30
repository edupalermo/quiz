package org.palermo.quiz.repository;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Repository
public class RoomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<Room> rowMapper = ((resultSet, i) -> {
        return Room.builder()
                .identification(resultSet.getString("IDENTIFICATION"))
                .creation(resultSet.getTimestamp("CREATION").toLocalDateTime())
                .lastActivity(resultSet.getTimestamp("LAST_ACTIVITY").toLocalDateTime())
                .build();
    });

    public Room create() {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("identification", generateRoomIdentification())
                .addValue("creation", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("lastActivity", Timestamp.valueOf(LocalDateTime.now()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update("INSERT INTO ROOM (IDENTIFICATION, CREATION, LAST_ACTIVITY) VALUES (:identification, :creation, :lastActivity)", parameterSource, keyHolder, new String[]{"ID"});
        if (rowsAffected != 1) {
            throw new RuntimeException("Insert error, rows affected "+ rowsAffected);
        }
        return getById(keyHolder.getKey().longValue());
    }

    private Room getById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject("SELECT IDENTIFICATION, CREATION, LAST_ACTIVITY FROM ROOM where ID = :id", parameterSource, rowMapper);
    }

    private String generateRoomIdentification() {
        Random random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
