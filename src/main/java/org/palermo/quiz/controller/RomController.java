package org.palermo.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class RomController {

    private final RoomService roomService;

    private static final Logger logger = LoggerFactory.getLogger(RomController.class);

    @RequestMapping("/room")
    public String room(Model model, HttpSession session) {
        Room room = roomService.create();
        model.addAttribute("roomIdentifier", room.getIdentifier());

        session.setAttribute("roomIdentifier", room.getIdentifier());

        return "room.html";
    }

    @RequestMapping("/selectRoom")
    public String selectRoom() {
        return "select-room.html";
    }

    @RequestMapping(value = "/setRoom", method = RequestMethod.POST)
    public String setRoom(Model model, HttpSession session, @RequestParam String roomIdentifier, Authentication authentication) {

        Room room = roomService.findByIdentifier(roomIdentifier).orElse(null);

        if (room == null) {
            model.addAttribute("noRoom", true);
            return "select-room.html";
        }
        else {
            session.setAttribute(SessionAttributes.ROOM_IDENTIFIER, room.getIdentifier());
            session.setAttribute(SessionAttributes.USER_EMAIL, authentication.getName());

            return "panel.html";
        }
    }
}
