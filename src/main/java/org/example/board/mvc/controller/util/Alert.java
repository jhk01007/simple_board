package org.example.board.mvc.controller.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.example.board.constant.ViewsPath.ROOT;


public class Alert {
    public static void setAlertInfo(HttpServletRequest req, String msg, String path) {
        req.setAttribute("msg", msg);
        req.setAttribute("path", path);
    }
}
