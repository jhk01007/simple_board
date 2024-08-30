package org.example.recipe_board.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image {
    private Long id;
    private Long recipe_id;
    private String originalName;
    private String savedPath;
}
