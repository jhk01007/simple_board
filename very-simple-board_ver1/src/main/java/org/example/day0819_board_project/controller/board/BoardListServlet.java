package org.example.day0819_board_project.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.day0819_board_project.constant.ViewsPath;
import org.example.day0819_board_project.domain.Board;
import org.example.day0819_board_project.service.BoardService;

import java.io.IOException;
import java.util.List;

import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/boards")
public class BoardListServlet extends HttpServlet {

    private BoardService boardService = BoardService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Board> boards = boardService.getBoards();
        req.setAttribute("boards", boards);
        req.getRequestDispatcher(ROOT + "board/board_list.jsp").forward(req, resp);
    }
}
