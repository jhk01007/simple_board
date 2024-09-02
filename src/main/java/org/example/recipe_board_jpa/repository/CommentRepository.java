package org.example.recipe_board_jpa.repository;


import org.apache.ibatis.annotations.Mapper;
import org.example.recipe_board_jpa.entity.Comment;
import org.example.recipe_board_jpa.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    int insert(Comment comment);
//    List<CommentDTO> selectListByBoardId(Long boardId); // 특정 글에 딸려 있는 모든 댓글 보기

    List<Comment> findByRecipeId(Long id);

}
