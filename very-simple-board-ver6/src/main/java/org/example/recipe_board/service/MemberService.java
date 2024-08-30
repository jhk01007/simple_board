package org.example.recipe_board.service;



import org.example.recipe_board.domain.Member;
import org.example.recipe_board.dto.LoginDTO;
import org.example.recipe_board.dto.SignupDTO;
import org.example.recipe_board.exception.LoginException;
import org.example.recipe_board.exception.MemberNotFoundException;
import org.example.recipe_board.object_mapper.MemberMapper;
import org.example.recipe_board.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MemberService {


    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * 회원가입
     */

    public void signUp(SignupDTO signupDTO) {
        Member member = memberMapper.toMember(signupDTO);
        memberRepository.insert(member);
    }

    /**
     * 로그인
     */
    public Member login(LoginDTO loginDTO) {
        Member member = memberMapper.toMember(loginDTO);
        String serviceId = member.getServiceId();
        String password = member.getPassword();
        Member login = memberRepository.findByServiceId(serviceId)
                .orElseThrow(() -> new LoginException("아이디가 존재하지 않습니다."));
        System.out.println(login);
        if (!login.getPassword().equals(password))
            throw new LoginException("비밀번호가 틀립니다.");

        return login;
    }

    /**
     * 회원 아이디 가져오기
     */

    public String getServiceIdById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원을 찾을 수 없습니다."))
                .getServiceId();
    }

}
