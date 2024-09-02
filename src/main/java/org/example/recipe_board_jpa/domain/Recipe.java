package org.example.recipe_board_jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private Long id;
    private Long writerId;
    private String foodName;
    private String ingredients;
    private String process;
    private Integer readCount;
    private String createdAt;
    private List<Image> images;
    private List<Comment> comments;

    public Recipe(Long id, Long writerId, String foodName, String ingredients, String process) {
        this.id = id;
        this.writerId = writerId;
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.process = process;
    }
}
