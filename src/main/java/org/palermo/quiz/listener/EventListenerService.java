package org.palermo.quiz.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palermo.quiz.domain.Account;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.domain.Session;
import org.palermo.quiz.listener.model.Info;
import org.palermo.quiz.repository.AccountRepository;
import org.palermo.quiz.repository.RoomRepository;
import org.palermo.quiz.repository.SessionRepository;
import org.palermo.quiz.service.GameService;
import org.palermo.quiz.service.SessionService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j
@Service
public class EventListenerService {

    private final SessionRepository sessionRepository;
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;

    private final SessionService sessionService;
    private final GameService gameService;

    private static final String TOPIC_ROOM = "/topic/room";
    private static final String TOPIC_USER = "/topic/user";

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {

        if (event.getMessage() instanceof GenericMessage) {
            GenericMessage genericMessage = (GenericMessage) event.getMessage();
            log.info("Destination: " + genericMessage.getHeaders().get("simpDestination"));
        }


        UsernameAccessor usernameAccessor = UsernameAccessor.wrap(event.getUser().getName());
        if (usernameAccessor.isUser()) {
            gameService.userConnected(usernameAccessor.getRoomIdentifier(), usernameAccessor.getAccountEmail());
        }
        else if (usernameAccessor.isRoom()) {
            gameService.roomConnected(usernameAccessor.getRoomIdentifier());
        }
        else {
            log.info("===========> Unknown session subscribe event: " + event.getSource() + " - " + event.getUser().getName());
        }
    }

    @EventListener
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {

        if (event.getMessage() instanceof GenericMessage) {
            GenericMessage genericMessage = (GenericMessage) event.getMessage();
            log.info("Destination: " + genericMessage.getHeaders().get("simpDestination"));
        }

        UsernameAccessor usernameAccessor = UsernameAccessor.wrap(event.getUser().getName());
        if (usernameAccessor.isUser()) {
            gameService.userDisconnected(usernameAccessor.getRoomIdentifier(), usernameAccessor.getAccountEmail());
        }
        else if (usernameAccessor.isRoom()) {
            gameService.roomDisconnected(usernameAccessor.getRoomIdentifier());
        }
        else {
            log.info("===========> Unknown session subscribe event: " + event.getSource() + " - " + event.getUser().getName());
        }
    }


    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        Message msg = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String sessionId = accessor.getSessionId();

        log.info("===========> Session Connected: " + event.getSource() + " - " + event.getUser().getName());
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        Message msg = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(msg);
        String sessionId = accessor.getSessionId();

        log.info("===========> Session Disconnected: " + event.getSource() + " - " + event.getUser().getName());
    }

}
