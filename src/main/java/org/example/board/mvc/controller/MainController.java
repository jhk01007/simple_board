package org.example.board.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping
    public String mainPage(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object login = session.getAttribute("login");

        if(login != null) {
            req.setAttribute("login", login.toString());
            return "main/main_after_login";
        } else
            return "main/main_before_login";
    }
}
