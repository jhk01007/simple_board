package org.example.day0819_board_project.controller.board;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.day0819_board_project.domain.Board;
import org.example.day0819_board_project.exception.AuthenticatedException;
import org.example.day0819_board_project.exception.BoardNotFoundException;
import org.example.day0819_board_project.service.BoardService;

import java.io.IOException;
import java.util.Optional;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;
import static org.example.day0819_board_project.constant.ViewsPath.ROOT;

@WebServlet("/boards/edit/*")
public class BoardEditServlet extends HttpServlet {
    private BoardService boardService = BoardService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long board_no = Long.valueOf(req.getPathInfo().split("/")[1]);// 만약 /boards/edit/4이라면 req.getPathInfo()시 /edit/4출력
        Board board = null;
        String login = null;
        try {
            board = boardService.read(board_no);
            HttpSession session = req.getSession();
            login = (String) session.getAttribute("login");
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("수정할 권한이 없습니다.");

            req.setAttribute("edit", board);
            req.getRequestDispatcher(ROOT + "board/edit_form.jsp").forward(req, resp);
        } catch (BoardNotFoundException | AuthenticatedException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath(), resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long board_no = Long.valueOf(req.getPathInfo().split("/")[1]);
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");

        try {
            Board board = boardService.read(board_no);
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("수정할 권한이 없습니다.");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            board.setTitle(title);
            board.setContent(content);

            boardService.addReadCount(board);
            alertAndRedirect(req, "수정되었습니다.", req.getContextPath() + "/boards/" + board_no, resp);
        } catch (BoardNotFoundException | AuthenticatedException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath(), resp);
        }


    }

}
