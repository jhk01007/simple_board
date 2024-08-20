package org.example.day0819_board_project.controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.day0819_board_project.constant.Alert;
import org.example.day0819_board_project.constant.ViewsPath;
import org.example.day0819_board_project.domain.Member;
import org.example.day0819_board_project.exception.IdDuplicateException;
import org.example.day0819_board_project.service.MemberService;

import java.io.IOException;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;
import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/members/signup")
public class SignUpServlet extends HttpServlet {

    private MemberService memberService = MemberService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ROOT + "member/signup_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = new Member(req.getParameter("memberid"), req.getParameter("password"));
        try {
            memberService.signUp(member);
            alertAndRedirect(req, "회원가입에 성공했습니다. 환영합니다.", req.getContextPath(), resp);
        } catch (IdDuplicateException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath() + "/signup", resp);
        }

    }
}
