package org.example.recipe_board_jpa.dto.join;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recipe_board_jpa.dto.ImageDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeWithServiceId {
    private Long id;
    private Long writerId;
    private String writer; // Member 테이블의 serviceId
    private String foodName;
    private String ingredients;
    private Integer readCount;
    private String createdAt;
    private String process;
    private List<ImageDTO> images = new ArrayList<>();
}
