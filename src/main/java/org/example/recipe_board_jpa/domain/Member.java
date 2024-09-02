package org.example.recipe_board_jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Member {
    private Long id;
    private String serviceId;
    private String password;
    private List<Recipe> recipes;
    private List<Comment> comments;

    public Member(String serviceId, String password) {
        this.serviceId = serviceId;
        this.password = password;
    }
}
