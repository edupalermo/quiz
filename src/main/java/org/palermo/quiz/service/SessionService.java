package org.palermo.quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palermo.quiz.domain.Account;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.domain.Session;
import org.palermo.quiz.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public void create(Room room, Account account) {
        sessionRepository.create(Session.builder()
                .account(account)
                .room(room)
                .creation(LocalDateTime.now())
                .points(0)
                .build());
    }

    public void delete(Room room, Account account) {
        sessionRepository.delete(room, account);
    }

    public List<Session> findByRoom(Room room) {
        return sessionRepository.findByRoom(room);
    }
}
