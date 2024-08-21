package org.example.board.mvc.service;



import org.example.board.domain.Board;
import org.example.board.exception.BoardNotFoundException;
import org.example.board.mvc.repository.BoardRepository;
import org.example.board.mvc.repository.BoardRepositoryMysql;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// SQL 실행은 리포지토리가 담당하고 나는 뭘 검사하고 판단하고 조작해내는 비즈니스 로직
@Service
public class BoardService {
    private BoardRepository repo;

    public BoardService(BoardRepository repo) {
        this.repo = repo;
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
