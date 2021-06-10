package org.palermo.quiz.service;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.repository.RoomRepository;
import org.palermo.quiz.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final SessionRepository sessionRepository;

    public Room create() {
        return roomRepository.create();
    }

    public Optional<Room> findByIdentifier(String identifier) {
        return roomRepository.findByIdentifier(identifier);
    }

    public void deleteIdleRooms() {
        roomRepository
                .findInactiveRoomsSince(LocalDateTime.now().minus(1, ChronoUnit.HOURS))
                .stream()
                .forEach(room -> {
                    sessionRepository.deleteAll(room);
                    roomRepository.delete(room);
                });
    }
}
