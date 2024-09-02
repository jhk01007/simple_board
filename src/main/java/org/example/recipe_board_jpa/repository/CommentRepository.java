package org.example.recipe_board_jpa.repository;


import org.apache.ibatis.annotations.Mapper;
import org.example.recipe_board_jpa.domain.Comment;
import org.example.recipe_board_jpa.dto.CommentDTO;

import java.util.List;

@Mapper
public interface CommentRepository {
    int insert(Comment comment);
    List<CommentDTO> selectListByBoardId(Long boardId); // 특정 글에 딸려 있는 모든 댓글 보기

}
