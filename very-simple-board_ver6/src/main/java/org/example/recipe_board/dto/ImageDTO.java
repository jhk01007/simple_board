package org.example.recipe_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private Long recipe_id;
    private String originalName;
    private String savedPath;
}
