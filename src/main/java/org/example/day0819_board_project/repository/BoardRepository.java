package org.example.day0819_board_project.repository;

import org.example.day0819_board_project.domain.Board;

import java.sql.SQLException;
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
