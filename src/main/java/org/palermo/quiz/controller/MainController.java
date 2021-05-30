package org.palermo.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    // Login form
    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }
}
