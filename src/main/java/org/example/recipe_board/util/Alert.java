package org.example.recipe_board.util;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public class Alert {
    public static void setAlertInfo(ModelAndView modelAndView, String msg, String path) {
        modelAndView.addObject("msg", msg);
        modelAndView.addObject("path", path);
    }

    public static void setAlertInfo(HttpServletRequest req, String msg, String path) {
        req.setAttribute("msg", msg);
        req.setAttribute("path", path);
    }
}
