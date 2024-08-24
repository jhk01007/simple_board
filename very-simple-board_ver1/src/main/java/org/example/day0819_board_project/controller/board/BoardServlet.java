package org.example.day0819_board_project.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.day0819_board_project.constant.Alert;
import org.example.day0819_board_project.domain.Board;
import org.example.day0819_board_project.exception.BoardNotFoundException;
import org.example.day0819_board_project.service.BoardService;

import java.io.IOException;
import java.util.Optional;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;
import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/boards/*")
public class BoardServlet extends HttpServlet {

    private BoardService boardService = BoardService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long board_no = Long.valueOf(req.getPathInfo().substring(1));
        try {
            Board board = boardService.read(board_no);
            boardService.addReadCount(board); // 조회수 1 증가 시키기

            req.setAttribute("board", board);
            req.getRequestDispatcher(ROOT + "board/board.jsp").forward(req, resp);
        } catch (BoardNotFoundException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath() + "/boards", resp);
        }
    }
}
