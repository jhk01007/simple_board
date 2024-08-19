package org.example.day0819_board_project.service;

import org.example.day0819_board_project.domain.Board;
import org.example.day0819_board_project.exception.BoardNotFoundException;
import org.example.day0819_board_project.repository.BoardRepository;
import org.example.day0819_board_project.repository.BoardRepositoryMysql;

import java.util.List;
import java.util.Optional;

// SQL 실행은 리포지토리가 담당하고 나는 뭘 검사하고 판단하고 조작해내는 비즈니스 로직
public class BoardService {
    private BoardRepository repo = BoardRepositoryMysql.getInstance();

    private BoardService(){}

    private static BoardService instance = new BoardService();
    public static BoardService getInstance() {
        return instance;
    }

    // 글 읽기 수행할 때 작성자와 읽는 사용자가 일치하는지 검사해서 조회수 증가 update 여부를 판단하거나
    // 이미 이 글을 읽은 사용자는 조회수가 중복해서 증가하지 않도록 검사하거나 하는 로직을 수행하는 메서드
    // public Board read(int id, int loginId) throws SQLException
    // 지금은 처리 X
    public Board read(Long id) {
        Optional<Board> board = repo.selectOne(id);
        if(board.isEmpty()) throw new BoardNotFoundException("해당 글을 찾을 수 없습니다.");
        return board.get();
    }

    public int write(Board board) {
        return repo.insert(board);
    }

    public int edit(Board board) {
        return repo.update(board);
    }

    public int delete(Long id) {
        return repo.delete(id);
    }

    public int addReadCount(Board board) {
        board.setReadCount(board.getReadCount() + 1);
        return repo.update(board);
    }

    public List<Board> getBoards() {
        return repo.selectAll();
    }
}
