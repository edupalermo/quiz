package org.palermo.quiz.repository;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Question;
import org.palermo.quiz.repository.mapper.QuestionRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Repository
public class QuestionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QuestionRowMapper questionRowMapper;

    private final static String SELECT_COLUMNS = "IDENTIFIER, FULL_PATH, CREATION";

    public Question create(Question question) {
        String identifier = generateQuestionIdentification();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("identifier", identifier)
                .addValue("fullPath", question.getFullPath())
                .addValue("creation", Timestamp.valueOf(question.getCreation()));

        int rowsAffected = jdbcTemplate.update("INSERT INTO QUESTION (IDENTIFIER, FULL_PATH, CREATION) VALUES (:identifier, :fullPath, :creation)", parameterSource);
        if (rowsAffected != 1) {
            throw new RuntimeException("Insert error, rows affected "+ rowsAffected);
        }
        return findByIdentifier(identifier);
    }

    public Question findByIdentifier(String identifier) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("identifier", identifier);
        return jdbcTemplate.queryForObject("SELECT " + SELECT_COLUMNS + " FROM QUESTION WHERE IDENTIFIER = :identifier", parameterSource, questionRowMapper.get());

    }

    public boolean exists(String fullPath) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("fullPath", fullPath);
            jdbcTemplate.queryForObject("SELECT " + SELECT_COLUMNS + " FROM QUESTION WHERE FULL_PATH = :fullPath", parameterSource, questionRowMapper.get());
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private String generateQuestionIdentification() {
        Random random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
