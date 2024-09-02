package org.example.recipe_board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private Long id;
    private Long recipeId;
    private String originalName;
    private String savedPath;
}
