package org.example.day0819_board_project.controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.day0819_board_project.constant.ViewsPath;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Object login = session.getAttribute("login");
        // 세션이 있다면 회원전용 페이지로
        if(login != null) {
            req.setAttribute("login", login.toString());
                req.getRequestDispatcher(ViewsPath.ROOT + "main/main_after_login.jsp").forward(req, resp);
        }
        // 세션이 없다면 비회원전용 페이지로
        else
            req.getRequestDispatcher(ViewsPath.ROOT + "main/main_before_login.jsp").forward(req, resp);
    }
}