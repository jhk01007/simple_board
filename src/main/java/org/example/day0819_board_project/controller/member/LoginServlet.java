package org.example.day0819_board_project.controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.day0819_board_project.exception.LoginException;
import org.example.day0819_board_project.service.MemberService;

import java.io.IOException;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;
import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private MemberService memberService = MemberService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ROOT + "member/login_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberId = req.getParameter("memberid");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("rememberMe");

        try {
            memberService.login(memberId, password);
            HttpSession session = req.getSession();
            session.setAttribute("login", memberId);
            // 아이디 기억하기 체크 시 쿠키 추가
            if (rememberMe != null)
                resp.addCookie(getRememberIdCookie(memberId));
            // 체크 해제시 기존의 저장된 쿠키 무효화
            else
                resp.addCookie(invalidateRememberIdCookie(req.getCookies()));

            alertAndRedirect(req, memberId + "님 환영합니다.", req.getContextPath(), resp);
        } catch (LoginException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath() + "/login", resp);
        }
    }

    private static Cookie getRememberIdCookie(String memberId) {
        Cookie cookie = new Cookie("rememberId", memberId);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(5 * 60 * 60);
        return cookie;
    }

    private static Cookie invalidateRememberIdCookie(Cookie[] cookies) {

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("rememberId")) {
                cookie.setMaxAge(0); // 쿠키 무효화
                return cookie;
            }
        }

        return null;
    }
}
