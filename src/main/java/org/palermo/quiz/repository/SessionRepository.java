package org.palermo.quiz.repository;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Account;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.domain.Session;
import org.palermo.quiz.repository.mapper.SessionRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SessionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SessionRowMapper sessionRowMapper;

    public Session create(Session session) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("roomIdentifier", session.getRoom().getIdentifier())
                .addValue("accountEmail", session.getAccount().getEmail())
                .addValue("points", 0)
                .addValue("creation", Timestamp.valueOf(LocalDateTime.now()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update("INSERT INTO SESSION (ROOM_ID, ACCOUNT_ID, POINTS, CREATION) VALUES (" +
                "   (SELECT ID FROM ROOM WHERE IDENTIFIER = :roomIdentifier), " +
                "   (SELECT ID FROM ACCOUNT WHERE EMAIL = :accountEmail), " +
                "   :points, " +
                "   :creation " +
                ")", parameterSource, keyHolder, new String[] {"ID"});
        if (rowsAffected != 1) {
            throw new RuntimeException("Insert error, rows affected "+ rowsAffected);
        }
        return findById(keyHolder.getKey().longValue());
    }

    public Session findById(long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject("SELECT " +
                "       SESSION.POINTS      SESSION_POINTS, " +
                "       SESSION.CREATION    SESSION_CREATION," +
                "       ACCOUNT.EMAIL       ACCOUNT_EMAIL," +
                "       ACCOUNT.CREATION    ACCOUNT_CREATION," +
                "       ROOM.IDENTIFIER     ROOM_IDENTIFIER," +
                "       ROOM.CREATION       ROOM_CREATION," +
                "       ROOM.LAST_ACTIVITY  ROOM_LAST_ACTIVITY" +
                "   FROM " +
                "       SESSION " +
                "       INNER JOIN ACCOUNT ON SESSION.ACCOUNT_ID = ACCOUNT.ID " +
                "       INNER JOIN ROOM ON SESSION.ROOM_ID = ROOM.ID " +
                "   WHERE SESSION.ID = :id ",
                parameterSource,
                sessionRowMapper.get("SESSION_", "ACCOUNT_", "ROOM_"));
    }

    public List<Session> findByRoom(Room room) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("roomIdentifier", room.getIdentifier());
        return jdbcTemplate.query("SELECT " +
                "       SESSION.POINTS      SESSION_POINTS, " +
                "       SESSION.CREATION    SESSION_CREATION," +
                "       ACCOUNT.EMAIL       ACCOUNT_EMAIL," +
                "       ACCOUNT.CREATION    ACCOUNT_CREATION," +
                "       ROOM.IDENTIFIER     ROOM_IDENTIFIER," +
                "       ROOM.CREATION       ROOM_CREATION," +
                "       ROOM.LAST_ACTIVITY  ROOM_LAST_ACTIVITY" +
                "   FROM " +
                "       SESSION " +
                "       INNER JOIN ACCOUNT ON SESSION.ACCOUNT_ID = ACCOUNT.ID " +
                "       INNER JOIN ROOM ON SESSION.ROOM_ID = ROOM.ID " +
                "   WHERE ROOM.IDENTIFIER = :roomIdentifier",
                parameterSource,
                sessionRowMapper.get("SESSION_", "ACCOUNT_", "ROOM_"));
    }

    public int deleteAll(Room room) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("roomIdentifier", room.getIdentifier());
        return jdbcTemplate.update("DELETE FROM SESSION WHERE ROOM_ID = " +
                " (SELECT ID FROM ROOM WHERE IDENTIFIER = :roomIdentifier) ",
                parameterSource);
    }

    public int delete(Room room, Account account) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("roomIdentifier", room.getIdentifier())
                .addValue("accountEmail", account.getEmail());
        return jdbcTemplate.update("DELETE FROM SESSION WHERE " +
                        "   ROOM_ID = (SELECT ID FROM ROOM WHERE IDENTIFIER = :roomIdentifier) " +
                        " AND " +
                        "   ACCOUNT_ID = (SELECT ID FROM ACCOUNT WHERE EMAIL = :accountEmail) ",
                parameterSource);
    }
}
