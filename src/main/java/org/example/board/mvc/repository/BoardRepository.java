package org.example.board.mvc.repository;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.board.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    int insert(Board board);
//    int update(Board board, String category) throws SQLException;
    int update(Board board);
    int delete(Long id);
    List<Board> selectAll();
    int selectCount();
    List<Board> selectPageList(@Param("sr") int startRow, @Param("cnt") int count);
    Optional<Board> selectOne(Long id);
}
