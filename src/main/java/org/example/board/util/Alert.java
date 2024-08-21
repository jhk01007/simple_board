package org.example.board.util;


import org.springframework.web.servlet.ModelAndView;

public class Alert {
    public static void setAlertInfo(ModelAndView modelAndView, String msg, String path) {
        modelAndView.addObject("msg", msg);
        modelAndView.addObject("path", path);
    }
}
