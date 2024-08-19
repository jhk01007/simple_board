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
import org.example.day0819_board_project.service.BoardService;

import java.io.IOException;

import static org.example.day0819_board_project.constant.Alert.alertAndRedirect;
import static org.example.day0819_board_project.constant.ViewsPath.*;

@WebServlet("/boards/write")
public class BoardWriteServlet extends HttpServlet {

    private BoardService boardService = BoardService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 로그인 된 사용자인지 체크
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");

        //로그인된 사용자라면 글 작성 가능
        if(login != null)
            req.getRequestDispatcher(ROOT + "board/write_form.jsp").forward(req, resp);
        else {
            req.setAttribute("msg", "접근할 수 없습니다. 글을 작성하려면 로그인을 해주세요");
            req.setAttribute("path", req.getContextPath());
            req.getRequestDispatcher(ROOT + "alert.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        HttpSession session = req.getSession();
        String writer = (String) session.getAttribute("login");

        //로그인된 사용자라면 글 제출 가능
        if(writer != null) {
            Board board = new Board(title, content, writer);
            boardService.write(board);
            alertAndRedirect(req, "글이 작성되었습니다.", req.getContextPath() + "/boards", resp);
            req.getRequestDispatcher(ROOT + "alert.jsp").forward(req, resp);
        } else {
            alertAndRedirect(req, "접근할 수 없습니다. 글을 작성하려면 로그인을 해주세요", req.getContextPath(), resp);
        }
    }
}
