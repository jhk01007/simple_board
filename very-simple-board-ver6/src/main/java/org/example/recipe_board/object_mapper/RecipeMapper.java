package org.example.recipe_board.object_mapper;

import org.example.recipe_board.domain.Recipe;
import org.example.recipe_board.dto.join.RecipeWithServiceId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RecipeMapper {

    public static Recipe toRecipe(RecipeWithServiceId recipeWithServiceId) {
        return new Recipe(
                recipeWithServiceId.getId(),
                null,
                recipeWithServiceId.getFoodName(),
                recipeWithServiceId.getIngredients(),
                recipeWithServiceId.getProcess(),
                recipeWithServiceId.getReadCount(),
                recipeWithServiceId.getCreatedAt(),
                recipeWithServiceId.getImages().stream().map(ImageMapper::toImage).toList()
                ,new ArrayList<>()
                );
    }
}
