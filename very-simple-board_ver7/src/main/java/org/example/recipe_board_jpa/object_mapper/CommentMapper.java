package org.example.recipe_board_jpa.object_mapper;

import org.example.recipe_board_jpa.entity.Comment;
import org.example.recipe_board_jpa.dto.CommentDTO;
import org.example.recipe_board_jpa.entity.Member;
import org.example.recipe_board_jpa.entity.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentMapper {
    public static Comment toComment(CommentDTO commentDTO) {
        Comment parent = new Comment();
        parent.setId(commentDTO.getParentId());
        Recipe recipe = new Recipe();
        recipe.setId(commentDTO.getRecipeId());
        Member writer = new Member();
        writer.setId(commentDTO.getWriterId());
        return new Comment(
                commentDTO.getId(),
                parent,
                recipe,
                writer,
                commentDTO.getContent(),
                commentDTO.getCreatedAt()
        );

    }

    public static CommentDTO toDTO(Comment comment) {
        Comment parent = comment.getParent();
        Long parentId = null;
        if(parent != null)
            parentId = parent.getId();


        return new CommentDTO(
                comment.getId(),
                parentId,
                comment.getRecipe().getId(),
                comment.getWriter().getId(),
                comment.getWriter().getServiceId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getReplies().stream().map(CommentMapper::toDTO).collect(Collectors.toList())
                );
    }
}
