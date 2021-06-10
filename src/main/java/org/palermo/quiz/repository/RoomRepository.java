package org.palermo.quiz.repository;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.repository.mapper.RoomRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Repository
public class RoomRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoomRowMapper roomRowMapper;

    private final static String SELECT_COLUMNS = "IDENTIFIER, CREATION, LAST_ACTIVITY";

    public Room create() {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("identifier", generateRoomIdentification())
                .addValue("creation", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("lastActivity", Timestamp.valueOf(LocalDateTime.now()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update("INSERT INTO ROOM (IDENTIFIER, CREATION, LAST_ACTIVITY) VALUES (:identifier, :creation, :lastActivity)", parameterSource, keyHolder, new String[]{"ID"});
        if (rowsAffected != 1) {
            throw new RuntimeException("Insert error, rows affected "+ rowsAffected);
        }
        return findById(keyHolder.getKey().longValue());
    }

    public Optional<Room> findByIdentifier(String identifier) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("identifier", identifier);
            return Optional.of(jdbcTemplate.queryForObject("SELECT " + SELECT_COLUMNS + " FROM ROOM WHERE IDENTIFIER = :identifier", parameterSource, roomRowMapper.get()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Room> findInactiveRoomsSince(LocalDateTime cutDateTime) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("lastActivity", Timestamp.valueOf(cutDateTime));
        return jdbcTemplate.query("SELECT " + SELECT_COLUMNS + " FROM ROOM WHERE LAST_ACTIVITY < :lastActivity", parameterSource, roomRowMapper.get());
    }

    public void delete(Room room) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("identifier", room.getIdentifier());
        int rowsAffected = jdbcTemplate.update("DELETE FROM ROOM WHERE IDENTIFIER = :identifier ", parameterSource);
        if (rowsAffected != 1) {
            throw new RuntimeException("Delete error, rows affected "+ rowsAffected);
        }

    }

    private Room findById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject("SELECT " + SELECT_COLUMNS + " FROM ROOM WHERE ID = :id", parameterSource, roomRowMapper.get());
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
