package org.palermo.quiz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UserController {

    @MessageMapping("/answer")
    public void answer(String answer) throws Exception {
        log.info("Answer: " + answer);
    }
}
