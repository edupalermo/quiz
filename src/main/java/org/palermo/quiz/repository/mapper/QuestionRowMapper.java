package org.palermo.quiz.repository.mapper;

import org.palermo.quiz.domain.Question;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionRowMapper {

    public RowMapper<Question> get() {
        return get("");
    }

    public RowMapper<Question> get(String prefix) {
        return ((resultSet, i) -> Question.builder()
                .identifier(resultSet.getString(prefix + "IDENTIFIER"))
                .creation(resultSet.getTimestamp(prefix + "CREATION").toLocalDateTime())
                .build());
    }
}
