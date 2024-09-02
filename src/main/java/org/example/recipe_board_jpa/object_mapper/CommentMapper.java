package org.example.recipe_board_jpa.object_mapper;

import org.example.recipe_board_jpa.domain.Comment;
import org.example.recipe_board_jpa.dto.CommentDTO;

public class CommentMapper {
    public static Comment toComment(CommentDTO commentDTO) {
        return new Comment(
                null,
                commentDTO.getParentId(),
                commentDTO.getRecipeId(),
                commentDTO.getWriterId(),
                commentDTO.getContent(),
                commentDTO.getCreatedAt());
    }
}
