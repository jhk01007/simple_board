package org.example.recipe_board_jpa.controller;

import org.example.recipe_board_jpa.dto.CommentDTO;
import org.example.recipe_board_jpa.service.RecipeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final RecipeService recipeService;

    public CommentController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/write")
//    @ResponseBody // 이게 있으면 String을 리턴하더라도 뷰 이름으로 처리하지않고 실제 문자열로 처리함
    public String write(CommentDTO commentDTO) {
        System.out.println(commentDTO);
        recipeService.writeComment(commentDTO);
        return "comment write success";
    }

    @GetMapping("/list/{id}")
//    @ResponseBody
    public ResponseEntity<?> list(@PathVariable("id") Long recipeId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return ResponseEntity.ok().headers(headers).body(recipeService.getComments(recipeId));
    }
}
