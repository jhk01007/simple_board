package org.example.board.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MyController {

    String process(HttpServletRequest req, HttpServletResponse resp);
}
