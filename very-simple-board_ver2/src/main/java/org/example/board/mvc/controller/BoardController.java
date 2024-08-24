package org.example.board.mvc.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.board.domain.Board;
import org.example.board.exception.AuthenticatedException;
import org.example.board.exception.BoardNotFoundException;
import org.example.board.mvc.controller.util.Alert;
import org.example.board.mvc.service.BoardService;

import java.util.List;
import java.util.Optional;

public class BoardController implements MyController {

    BoardService boardService = BoardService.getInstance();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse resp) {
        String action = Optional.ofNullable(req.getPathInfo()).orElse("");
        String viewName = "";
        if (action.isEmpty()) {
            // 게시판 글 목록
            viewName = viewBoardList(req); // GET: 글 목록 요청
        } else if (action.startsWith("/write")) {

            if (req.getMethod().equals("GET"))
                viewName = getWriteForm(req); // GET: 작성 폼 요청
            else if (req.getMethod().equals("POST"))
                viewName = writeBoard(req); // POST: 글 작성 요청

        } else if (action.startsWith("/edit")) {

            // 게시판 글 수정
            Long editId = Long.valueOf(action.replace("/edit/", ""));
            if (req.getMethod().equals("GET"))
                viewName = getEditForm(req, editId); // GET: 수정 폼 요청
            else if (req.getMethod().equals("POST"))
                viewName = editBoard(req, editId); // POST: 수정 요청

        } else if (action.startsWith("/delete")) {
            // 게시판 글 삭제
            Long deleteId = Long.valueOf(action.replace("/delete/", ""));
            viewName = deleteBoard(req, deleteId); // POST: 삭제 요청
        } else {
            // 게시판 글 상세정보
            Long viewId = Long.valueOf(action.substring(1));
            viewName = viewBoard(req, viewId); // GET: 글 상세정보 요청
        }

        return viewName;
    }

    /**
     * [GET] /boards: 글 목록 요청
     * @param req
     * @return
     */
    private String viewBoardList(HttpServletRequest req) {
        List<Board> boards = boardService.getBoards();
        req.setAttribute("boards", boards);
        return "board/board_list";
    }

    /**
     * [GET] /boards/*: 글 상세정보 요청
     * @param req
     * @param viewId
     * @return
     */
    private String viewBoard(HttpServletRequest req, Long viewId) {
        try {
            Board board = boardService.read(viewId);
            boardService.addReadCount(board); // 조회수 1 증가 시키기

            req.setAttribute("board", board);
            return "board/board";
        } catch (BoardNotFoundException e) {
            Alert.setAlertInfo(req, e.getMessage(), req.getContextPath() + "/boards");
            return "alert";
        }
    }

    /**
     * [GET]: /boards/write: 글 작성 폼 요청
     * @param req
     * @return
     */
    private String getWriteForm(HttpServletRequest req) {
        try {
            // 로그인 된 사용자인지 체크
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("login");

            //로그인된 사용자라면 글 작성 가능
            if (login == null)
                throw new AuthenticatedException("글을 작성할 권한이 없습니다. 로그인을 해주세요.");

            return "board/write_form";
        } catch (AuthenticatedException e) {
            Alert.setAlertInfo(req, e.getMessage(), req.getContextPath());
            return "alert";
        }
    }

    /**
     * [POST] /boards/write: 글 작성 요청
     * @param req
     * @return
     */
    private String writeBoard(HttpServletRequest req) {
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
            Alert.setAlertInfo(req, "글이 작성되었습니다.", req.getContextPath() + "/boards");
        } catch (AuthenticatedException e) {
            Alert.setAlertInfo(req, e.getMessage(), req.getContextPath());
        }

        return "alert";
    }

    /**
     * [GET] /boards/edit/*: 글 수정폼 요청
     * @param req
     * @param editId
     * @return
     */
    private String getEditForm(HttpServletRequest req, Long editId) {
        try {
            Board board = boardService.read(editId);
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("login");
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("수정할 권한이 없습니다.");

            req.setAttribute("edit", board);
            return "board/edit_form";
        } catch (BoardNotFoundException | AuthenticatedException e) {
            Alert.setAlertInfo(req, e.getMessage(), req.getContextPath());
            return "alert";
        }
    }

    /**
     * [POST] /boards/edit/*: 글 수정 요청
     * @param req
     * @param editId
     * @return
     */
    private String editBoard(HttpServletRequest req, Long editId) {
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");

        try {
            Board board = boardService.read(editId);
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("수정할 권한이 없습니다.");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            board.setTitle(title);
            board.setContent(content);

            boardService.addReadCount(board);

            Alert.setAlertInfo(req, "수정되었습니다.", req.getContextPath() + "/boards/" + editId);
        } catch (BoardNotFoundException | AuthenticatedException e) {
            Alert.setAlertInfo(req, e.getMessage(), req.getContextPath());
        }
        return "alert";
    }


    /**
     * [POST] /boards/delete/*: 글 삭제 요청
     * @param req
     * @param deleteId
     * @return
     */
    private String deleteBoard(HttpServletRequest req, Long deleteId) {
        Board board = null;
        try {
            board = boardService.read(deleteId);
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("login");
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("삭제할 권한이 없습니다.");

            boardService.delete(deleteId);
            Alert.setAlertInfo(req, "삭제되었습니다.", req.getContextPath() + "/boards");
        } catch (BoardNotFoundException | AuthenticatedException e) {
            Alert.setAlertInfo(req, e.getMessage(), req.getContextPath() + "/boards");
        }

        return "alert";
    }


}
