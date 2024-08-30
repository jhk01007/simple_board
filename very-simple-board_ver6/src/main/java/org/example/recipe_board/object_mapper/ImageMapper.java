package org.example.recipe_board.object_mapper;

import org.example.recipe_board.domain.Image;
import org.example.recipe_board.dto.ImageDTO;

public class ImageMapper {

    public static Image toImage(ImageDTO imageDTO) {
        return new Image(imageDTO.getId(), imageDTO.getRecipe_id(), imageDTO.getOriginalName(), imageDTO.getSavedPath());
    }
}
