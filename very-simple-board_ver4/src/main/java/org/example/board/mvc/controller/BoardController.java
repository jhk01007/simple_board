package org.example.board.mvc.controller;

import jakarta.servlet.http.HttpSession;
import org.example.board.domain.Board;
import org.example.board.dto.EditDto;
import org.example.board.dto.WriteDto;
import org.example.board.exception.AuthenticatedException;
import org.example.board.exception.BoardNotFoundException;
import org.example.board.mvc.service.BoardService;
import org.example.board.util.Alert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.example.board.util.Alert.setAlertInfo;
import static org.example.board.util.AppContext.CONTEXT_PATH;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ModelAndView getBoardsList() {
        List<Board> boards = boardService.getBoards();
        ModelAndView modelAndView = new ModelAndView("board/board_list");
        modelAndView.addObject("boards", boards);

        return modelAndView;

    }

    @GetMapping("/{board_id}")
    public ModelAndView getBoardInfo(@PathVariable("board_id") Long board_id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Board board = boardService.read(board_id);
            boardService.addReadCount(board); // 조회수 1 증가 시키기

            modelAndView.addObject("board", board);
            modelAndView.setViewName("board/board");
        } catch (BoardNotFoundException e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH + "/boards");
            modelAndView.setViewName("alert");
        }
        return modelAndView;
    }

    @GetMapping("/write")
    public ModelAndView writeForm(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("board/write_form");

        return modelAndView;
    }

    @PostMapping("/write")
    public ModelAndView writeBoard(@ModelAttribute WriteDto writeDto, HttpSession session) {
        String title = writeDto.getTitle();
        String content = writeDto.getContent();
        String writer = (String) session.getAttribute("login");

        ModelAndView modelAndView = new ModelAndView("alert");

        try {
            //로그인된 사용자라면 글 제출 가능
            if (writer == null)
                throw new AuthenticatedException("글을 작성할 권한이 없습니다. 로그인 해주세요.");

            Board board = new Board(title, content, writer);
            boardService.write(board);

            setAlertInfo(modelAndView, "글이 작성되었습니다.", CONTEXT_PATH + "/boards");
        } catch (AuthenticatedException e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH);
        }

        return modelAndView;
    }

    @GetMapping("/edit/{board_id}")
    public ModelAndView editForm(@PathVariable("board_id") Long board_id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Board board = boardService.read(board_id);
            String login = (String) session.getAttribute("login");
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("수정할 권한이 없습니다.");

            modelAndView.addObject("edit", board);
            modelAndView.setViewName("board/edit_form");
        } catch (BoardNotFoundException | AuthenticatedException e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH);
            modelAndView.setViewName("alert");
        }

        return modelAndView;
    }

    @PostMapping("/edit/{board_id}")
    public ModelAndView edit(@PathVariable("board_id") Long board_id, @ModelAttribute EditDto editDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("alert");
        String login = (String) session.getAttribute("login");

        try {
            Board board = boardService.read(board_id);
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("수정할 권한이 없습니다.");
            String title = editDto.getTitle();
            String content = editDto.getContent();
            board.setTitle(title);
            board.setContent(content);

            boardService.addReadCount(board);

            setAlertInfo(modelAndView, "수정되었습니다.", CONTEXT_PATH + "/boards/" + board_id);
        } catch (BoardNotFoundException | AuthenticatedException e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH);
        }
        return modelAndView;
    }

    @PostMapping("/delete/{board_id}")
    public ModelAndView deleteBoard(@PathVariable("board_id") Long board_id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("alert");
        Board board = null;
        try {
            board = boardService.read(board_id);
            String login = (String) session.getAttribute("login");
            if (!board.getWriter().equals(login))
                throw new AuthenticatedException("삭제할 권한이 없습니다.");

            boardService.delete(board_id);
            setAlertInfo(modelAndView, "삭제되었습니다.", CONTEXT_PATH + "/boards");
        } catch (BoardNotFoundException | AuthenticatedException e) {
            setAlertInfo(modelAndView, e.getMessage(), CONTEXT_PATH + "/boards");
        }

        return modelAndView;
    }

}
