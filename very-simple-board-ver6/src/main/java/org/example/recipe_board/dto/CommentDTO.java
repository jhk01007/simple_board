package org.example.recipe_board.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Long recipeId;
    private Long writerId; // 얘는 데이터베이스 상의 기본키
    private String writer; // 얘는 서비스 id
    private String content;
    private String createdAt;
    private List<CommentDTO> children = new ArrayList<>();
}
