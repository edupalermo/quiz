package org.palermo.quiz.config.websocket;

import org.palermo.quiz.controller.SessionAttributes;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                String user = (String) session.getAttribute(SessionAttributes.USER_EMAIL);
                String roomIdentifier = (String) session.getAttribute(SessionAttributes.ROOM_IDENTIFIER);
                if (user != null && roomIdentifier != null) {
                    return new StompPrincipal("room: " + roomIdentifier + " user: " + user);
                }
                if (roomIdentifier != null) {
                    return new StompPrincipal("room: " + roomIdentifier);
                }
            }
        }

        throw new RuntimeException("Who is this person?");
    }

}