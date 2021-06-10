package org.palermo.quiz.repository;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Account;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.repository.mapper.AccountRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AccountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AccountRowMapper accountRowMapper;

    public Optional<Account> findByEmail(String email) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("email", email);
            return Optional.of(jdbcTemplate.queryForObject("SELECT EMAIL, CREATION FROM ACCOUNT WHERE EMAIL = :email", parameterSource, accountRowMapper.get()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
