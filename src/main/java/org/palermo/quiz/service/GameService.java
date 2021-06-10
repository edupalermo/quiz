package org.palermo.quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palermo.quiz.domain.Account;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.domain.Session;
import org.palermo.quiz.event.ShowMessageEvent;
import org.palermo.quiz.event.UpdateUsersEvent;
import org.palermo.quiz.event.model.User;
import org.palermo.quiz.listener.UsernameAccessor;
import org.palermo.quiz.repository.RoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

    private final RoomRepository roomService;
    private final SessionService sessionService;
    private final AccountService accountService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public void roomConnected(String roomIdentifier) {
        log.info("Room {} connected", roomIdentifier);

        Room room = roomService.findByIdentifier(roomIdentifier).orElseThrow(() -> new RuntimeException("No room found for identifier " + roomIdentifier));
        List<Session> sessions = sessionService.findByRoom(room);

        if (sessions.isEmpty()) {
            simpMessagingTemplate.convertAndSendToUser(UsernameAccessor.generate(room.getIdentifier()), "/topic/room", ShowMessageEvent
                    .builder()
                    .roomIdentifier(roomIdentifier)
                    .message("Room Identification " + roomIdentifier)
                    .build());
        }
        else {
            throw new RuntimeException("Not implemented");
        }
    }

    public void roomDisconnected(String roomIdentifier) {
        log.info("Room {} disconnected ", roomIdentifier);
    }

    public void userDisconnected(String roomIdentifier, String accountEmail) {
        log.info("User {} disconnected from room {}", accountEmail, roomIdentifier);

        Room room = roomService.findByIdentifier(roomIdentifier).orElseThrow(() -> new RuntimeException("No room found for identifier " + roomIdentifier));
        Account account = accountService.findByEmail(accountEmail).orElseThrow(() -> new RuntimeException("No account found with email " + accountEmail));

        sessionService.delete(room, account);
        sendUpdateUsersEventToRoom(room);


        evaluateQuestionSituation(room);
    }

    public void userConnected(String roomIdentifier, String accountEmail) {
        log.info("User {} connected to room {}", accountEmail, roomIdentifier);

        Room room = roomService.findByIdentifier(roomIdentifier).orElseThrow(() -> new RuntimeException("No room found for identifier " + roomIdentifier));
        Account account = accountService.findByEmail(accountEmail).orElseThrow(() -> new RuntimeException("No account found for email " + accountEmail));

        // Maybe we should check the existence of the session first

        sessionService.create(room, account);

        sendUpdateUsersEventToRoom(room);

        evaluateQuestionSituation(room);
    }

    private void sendUpdateUsersEventToRoom(Room room) {
        UpdateUsersEvent updateUsersEvent = UpdateUsersEvent.builder()
                .roomIdentifier(room.getIdentifier())
                .users(sessionService
                        .findByRoom(room)
                        .stream()
                        .map(session -> User.builder()
                                .nickname(session.getAccount().getEmail())
                                .points(session.getPoints()).build())
                        .collect(Collectors.toList()))
                .build();
        simpMessagingTemplate.convertAndSendToUser(UsernameAccessor.generate(room.getIdentifier()), "/topic/room", updateUsersEvent);
    }

    private void evaluateQuestionSituation(Room room) {
        if (doesRoomNeedQuestion(room)) {
            sendQuestionToRoom(room);
            sendOptionsToUsersOfARoom(room);
        }
    }

    private boolean doesRoomNeedQuestion(Room room) {
        return false;
    }

    private void startQuestion(Room room) {
        sendQuestionToRoom(room);
        sendOptionsToUsersOfARoom(room);
    }

    private void sendQuestionToRoom(Room room) {

    }

    private void sendOptionsToUsersOfARoom(Room room) {

    }
}
