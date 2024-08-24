package org.example.board.mvc.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// /main
public class MainController implements MyController{
    @Override
    public String process(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Object login = session.getAttribute("login");
        // 세션이 있다면 회원전용 페이지로
        if(login != null) {
            req.setAttribute("login", login.toString());
            return "main/main_after_login";
        }
        // 세션이 없다면 비회원전용 페이지로
        else
            return "main/main_before_login";
    }
}
