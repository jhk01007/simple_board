package org.example.day0819_board_project.controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.day0819_board_project.constant.Alert;
import org.example.day0819_board_project.constant.ViewsPath;

import java.io.IOException;

import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        Alert.alertAndRedirect(req, "로그아웃 되었습니다.", req.getContextPath(), resp);
    }
}
