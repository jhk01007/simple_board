package org.example.day0819_board_project.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.day0819_board_project.constant.Alert;
import org.example.day0819_board_project.domain.Board;
import org.example.day0819_board_project.exception.AuthenticatedException;
import org.example.day0819_board_project.exception.BoardNotFoundException;
import org.example.day0819_board_project.service.BoardService;

import java.io.IOException;
import java.util.Optional;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;

@WebServlet("/boards/delete/*")
public class BoardDeleteServlet extends HttpServlet {
    private final BoardService boardService = BoardService.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long board_no = Long.valueOf(req.getPathInfo().split("/")[1]);
        Board board = null;
        try {
            board = boardService.read(board_no);
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("login");
            if(!board.getWriter().equals(login))
                throw new AuthenticatedException("삭제할 권한이 없습니다.");

            boardService.delete(board_no);
            alertAndRedirect(req, "삭제되었습니다.", req.getContextPath() + "/boards", resp);
        } catch (BoardNotFoundException | AuthenticatedException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath() + "/boards", resp);
        }
    }
}
