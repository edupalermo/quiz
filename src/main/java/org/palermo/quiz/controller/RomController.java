package org.palermo.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.palermo.quiz.domain.Room;
import org.palermo.quiz.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class RomController {

    private final RoomService roomService;

    private static final Logger logger = LoggerFactory.getLogger(RomController.class);

    @RequestMapping("/room")
    public String room(Model model) {
        Room room = roomService.create();
        model.addAttribute("roomId", room.getIdentification());
        return "room.html";
    }

    @RequestMapping("/selectRoom")
    public String selectRoom() {
        return "select-room.html";
    }

    @RequestMapping(value = "/setRoom", method = RequestMethod.POST)
    public String setRoom(Model model) {

        model.addAttribute("noRoom", true);
        return "select-room.html";
    }
}
