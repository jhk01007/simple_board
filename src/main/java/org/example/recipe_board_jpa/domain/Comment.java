package org.example.recipe_board_jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long parentId;
    private Long recipeId;
    private Long writerId;
    private String content;
    private String createdAt;

}
