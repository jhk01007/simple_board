package org.example.recipe_board_jpa.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.recipe_board_jpa.entity.Member;
import org.example.recipe_board_jpa.dto.LoginDTO;
import org.example.recipe_board_jpa.dto.SignupDTO;
import org.example.recipe_board_jpa.exception.IdDuplicateException;
import org.example.recipe_board_jpa.exception.LoginException;
import org.example.recipe_board_jpa.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.example.recipe_board_jpa.util.Alert.setAlertInfo;


@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService, MemberService memberService1) {
        this.memberService = memberService1;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "member/signup_form";
    }

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute SignupDTO signupDTO) {
        ModelAndView modelAndView = new ModelAndView("alert");


        try {
            memberService.signUp(signupDTO);
            setAlertInfo(modelAndView, "회원가입에 성공했습니다. 환영합니다.", "/main");
        } catch (IdDuplicateException e) {
            setAlertInfo(modelAndView, e.getMessage(), "/members/signup");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login_form";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginDTO loginDTO, HttpServletRequest req, HttpServletResponse resp) {

        ModelAndView modelAndView = new ModelAndView("alert");

        try {
            Member login = memberService.login(loginDTO);
            HttpSession session = req.getSession();
            session.setAttribute("login", login.getId()); // 세션 추가

            // 아이디 기억하기 체크 시 쿠키 추가
            if(loginDTO.isRememberMe()) {
                resp.addCookie(getRememberIdCookie(login.getServiceId()));
            }
            // 체크 해제시 기존의 저장된 쿠키 무효화
            else  {
                Optional<Cookie> invalidatedCookie = Optional.ofNullable(invalidateRememberIdCookie(req.getCookies()));
                invalidatedCookie.ifPresent(resp::addCookie);
            }
            setAlertInfo(modelAndView, login.getServiceId() + "님 환영합니다.",  "/main");
        } catch (LoginException e) {
            setAlertInfo(modelAndView, e.getMessage(),  "/members/login");
        }

        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("alert");
        session.invalidate();
        setAlertInfo(modelAndView, "로그아웃 되었습니다", "/main");

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
