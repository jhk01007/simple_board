package org.example.board.mvc.repository;


import org.example.board.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    int insert(Board board);
//    int update(Board board, String category) throws SQLException;
    int update(Board board);
    int delete(Long id);
    List<Board> selectAll();
    Optional<Board> selectOne(Long id);
}
