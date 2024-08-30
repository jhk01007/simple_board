package org.example.recipe_board.controller;

import jakarta.servlet.http.HttpSession;
import org.example.recipe_board.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class MainController {

    private final MemberService memberService;

    public MainController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ModelAndView mainPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Object login = session.getAttribute("login");

        if(login != null) {
            modelAndView.addObject("login", memberService.getServiceIdById((Long) login));
            modelAndView.setViewName("main/main_after_login");
        } else
            modelAndView.setViewName("main/main_before_login");

        return modelAndView;
    }
}
