package org.example.recipe_board.object_mapper;

import org.example.recipe_board.domain.Comment;
import org.example.recipe_board.dto.CommentDTO;

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
