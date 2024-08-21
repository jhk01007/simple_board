package org.example.board.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping
    public ModelAndView mainPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Object login = session.getAttribute("login");

        if(login != null) {
            modelAndView.addObject("login", login.toString());
            modelAndView.setViewName("main/main_after_login");
        } else
            modelAndView.setViewName("main/main_before_login");

        return modelAndView;
    }
}
