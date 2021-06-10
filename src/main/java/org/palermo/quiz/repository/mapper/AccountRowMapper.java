package org.palermo.quiz.repository.mapper;

import org.palermo.quiz.domain.Account;
import org.palermo.quiz.domain.Room;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountRowMapper {

    public RowMapper<Account> get() {
        return get("");
    }

    public RowMapper<Account> get(String prefix) {
        return (resultSet, i) -> Account.builder()
                .email(resultSet.getString(prefix + "EMAIL"))
                .creation(resultSet.getTimestamp(prefix + "CREATION").toLocalDateTime())
                .build();
    }
}
