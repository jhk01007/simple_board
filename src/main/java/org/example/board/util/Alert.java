package org.example.board.util;

import jakarta.servlet.http.HttpServletRequest;


public class Alert {
    public static void setAlertInfo(HttpServletRequest req, String msg, String path) {
        req.setAttribute("msg", msg);
        req.setAttribute("path", path);
    }
}
