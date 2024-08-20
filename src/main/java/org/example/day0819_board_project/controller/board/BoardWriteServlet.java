package org.example.day0819_board_project.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.day0819_board_project.constant.Alert;
import org.example.day0819_board_project.constant.ViewsPath;
import org.example.day0819_board_project.domain.Board;
import org.example.day0819_board_project.exception.AuthenticatedException;
import org.example.day0819_board_project.service.BoardService;

import java.io.IOException;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;
import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/boards/write")
public class BoardWriteServlet extends HttpServlet {

    private BoardService boardService = BoardService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // 로그인 된 사용자인지 체크
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("login");

            //로그인된 사용자라면 글 작성 가능
            if (login == null)
                throw new AuthenticatedException("글을 작성할 권한이 없습니다. 로그인을 해주세요.");

            req.getRequestDispatcher(ROOT + "board/write_form.jsp").forward(req, resp);
        } catch (AuthenticatedException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath(), resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        HttpSession session = req.getSession();
        String writer = (String) session.getAttribute("login");

        try {
            //로그인된 사용자라면 글 제출 가능
            if (writer == null)
                throw new AuthenticatedException("글을 작성할 권한이 없습니다. 로그인 해주세요.");

            Board board = new Board(title, content, writer);
            boardService.write(board);
            alertAndRedirect(req, "글이 작성되었습니다.", req.getContextPath() + "/boards", resp);
        } catch (AuthenticatedException e) {
            alertAndRedirect(req, e.getMessage(), req.getContextPath(), resp);
        }
    }
}
