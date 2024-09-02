package org.example.recipe_board_jpa.object_mapper;

import org.example.recipe_board_jpa.entity.Image;
import org.example.recipe_board_jpa.dto.ImageDTO;

public class ImageMapper {

    public static Image toImage(ImageDTO imageDTO) {
//        return new Image(imageDTO.getId(), imageDTO.getRecipe_id(), imageDTO.getOriginalName(), imageDTO.getSavedPath());

        return new Image();
    }

    public static ImageDTO toDTO(Image image) {
        return new ImageDTO(
                image.getId(),
                image.getRecipe().getId(),
                image.getOriginalName(),
                image.getSavedPath()
        );
    }
}
