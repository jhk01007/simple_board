package org.example.recipe_board_jpa.object_mapper;

import org.example.recipe_board_jpa.dto.ImageDTO;
import org.example.recipe_board_jpa.entity.Recipe;
import org.example.recipe_board_jpa.dto.join.RecipeWithServiceId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static RecipeWithServiceId toDTO(Recipe recipe) {

        return new RecipeWithServiceId(
                recipe.getId(),
                recipe.getWriter().getId(),
                recipe.getWriter().getServiceId(),
                recipe.getFoodName(),
                recipe.getIngredients(),
                recipe.getReadCount(),
                recipe.getCreatedAt(),
                recipe.getProcess(),
                recipe.getImages().stream().map(ImageMapper::toDTO).collect(Collectors.toList())
        );
    }
}
