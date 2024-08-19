package org.example.day0819_board_project.constant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.example.day0819_board_project.constant.ViewsPath.ROOT;

public class Alert {
    public static void alertAndRedirect(HttpServletRequest req, String msg, String path, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("msg", msg);
        req.setAttribute("path", path);
        req.getRequestDispatcher(ROOT + "alert.jsp").forward(req, resp);
    }
}
