package org.example.recipe_board_jpa.object_mapper;

import org.example.recipe_board_jpa.domain.Member;
import org.example.recipe_board_jpa.dto.LoginDTO;
import org.example.recipe_board_jpa.dto.SignupDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(SignupDTO signupDTO) {
        return new Member(signupDTO.getServiceId(), signupDTO.getPassword());
    }

    public Member toMember(LoginDTO loginDTO) {
        return new Member(loginDTO.getServiceId(), loginDTO.getPassword());
    }
}
