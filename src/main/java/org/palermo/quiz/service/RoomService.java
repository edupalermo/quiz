package org.palermo.quiz.service;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.repository.RoomRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public Room create() {
        return roomRepository.create();
    }
}
