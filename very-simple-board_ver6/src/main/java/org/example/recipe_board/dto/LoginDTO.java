package org.example.recipe_board.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String serviceId;
    private String password;
    private boolean rememberMe;
}
