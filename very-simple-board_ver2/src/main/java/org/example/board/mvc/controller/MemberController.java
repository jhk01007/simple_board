package org.example.board.mvc.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.board.domain.Member;
import org.example.board.exception.IdDuplicateException;
import org.example.board.exception.LoginException;
import org.example.board.mvc.controller.util.Alert;
import org.example.board.mvc.service.MemberService;

import java.util.Optional;

import static org.example.board.mvc.controller.util.Alert.setAlertInfo;

public class MemberController implements MyController{

    MemberService memberService = MemberService.getInstance();
    @Override
    public String process(HttpServletRequest req, HttpServletResponse resp) {

        String action = Optional.ofNullable(req.getPathInfo()).orElse("");
        System.out.println(action);
        String viewName = "";

        if(action.equals("/signup")) {
            if(req.getMethod().equals("GET"))
                viewName = getSignUpForm(); // GET: 회원가입 폼 요청
            else if(req.getMethod().equals("POST"))
                viewName = signup(req); // POST: 회원가입 요청

        } else if(action.equals("/login")) {
            if(req.getMethod().equals("GET"))
                viewName = getLoginForm(); // GET: 로그인 폼 요청
            else if(req.getMethod().equals("POST"))
                viewName = login(req, resp); // POST: 회원가입 요청
        } else if (action.equals("/logout")) {
            viewName = logout(req); // POST: 로그아웃 요청
        }
        return viewName;
    }

    private String getSignUpForm() {
        return "member/signup_form";
    }

    private String signup(HttpServletRequest req) {
        Member member = new Member(req.getParameter("memberid"), req.getParameter("password"));
        try {
            memberService.signUp(member);
            setAlertInfo(req, "회원가입에 성공했습니다. 환영합니다.", req.getContextPath() + "/main");
        } catch (IdDuplicateException e) {
            setAlertInfo(req, e.getMessage(), req.getContextPath() + "/members/signup");
        }
        return "alert";
    }

    /**
     * [GET] /members/login: 로그인 폼 요청
     * @return
     */
    private String getLoginForm() {
        return "member/login_form";
    }

    /**
     * [POST] /members/login: 로그인 요청
     * @param req
     * @param resp
     * @return
     */
    private String login(HttpServletRequest req, HttpServletResponse resp) {
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
            else {
                Optional<Cookie> invalidatedCookie = Optional.ofNullable(invalidateRememberIdCookie(req.getCookies()));
                invalidatedCookie.ifPresent(resp::addCookie);
            }

            setAlertInfo(req, memberId + "님 환영합니다.", req.getContextPath() + "/main");
        } catch (LoginException e) {
            setAlertInfo(req, e.getMessage(), req.getContextPath() + "/members/login");
        }

        return "alert";
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

    /**
     * [POST]/members/logout: 로그아웃 요청
     * @param req
     * @return
     */
    private String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();

        setAlertInfo(req, "로그아웃 되었습니다.", req.getContextPath());

        return "alert";
    }
}
