package org.example.board.mvc.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.board.domain.Member;
import org.example.board.dto.LoginDTO;
import org.example.board.dto.SignUpDTO;
import org.example.board.exception.IdDuplicateException;
import org.example.board.mvc.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.example.board.util.Alert.setAlertInfo;
import static org.example.board.util.AppContext.CONTEXT_PATH;

@Controller
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup_form";
    }

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute SignUpDTO signUpDTO) {
        Member member = new Member(signUpDTO.getMemberid(), signUpDTO.getPassword());
        ModelAndView modelAndView = new ModelAndView("alert");


        try {
            memberService.signUp(member);
            setAlertInfo(modelAndView, "회원가입에 성공했습니다. 환영합니다.", CONTEXT_PATH + "/main");
        } catch (IdDuplicateException e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH + "/members/signup");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login_form";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse resp) {
        String memberId = loginDTO.getMemberId();
        String password = loginDTO.getPassword();
        String rememberMe = loginDTO.getRememberMe();
        ModelAndView modelAndView = new ModelAndView("alert");

        try {
            memberService.login(memberId, password);
            HttpSession session = req.getSession();
            session.setAttribute("login", memberId);

            // 아이디 기억하기 체크 시 쿠키 추가
            if(rememberMe != null) {
                resp.addCookie(getRememberIdCookie(memberId));
            }
            // 체크 해제시 기존의 저장된 쿠키 무효화
            else  {
                Optional<Cookie> invalidatedCookie = Optional.ofNullable(invalidateRememberIdCookie(req.getCookies()));
                invalidatedCookie.ifPresent(resp::addCookie);
            }
            setAlertInfo(modelAndView, memberId + "님 환영합니다.", CONTEXT_PATH + "/main");
        } catch (Exception e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH + "/members/login");
        }

        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("alert");
        session.invalidate();
        setAlertInfo(modelAndView, "로그아웃 되었습니다", CONTEXT_PATH);

        return modelAndView;
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
