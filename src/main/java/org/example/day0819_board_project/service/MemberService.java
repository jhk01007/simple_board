package org.example.day0819_board_project.service;


import org.example.day0819_board_project.domain.Member;
import org.example.day0819_board_project.exception.IdDuplicateException;
import org.example.day0819_board_project.exception.InsertionFailureException;
import org.example.day0819_board_project.exception.LoginException;
import org.example.day0819_board_project.repository.MemberRepository;
import org.example.day0819_board_project.repository.MemberRepositoryImpl;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class MemberService {

    private MemberService() {
    }

    private static MemberService memberService = new MemberService();


    private MemberRepository memberRepository = MemberRepositoryImpl.getInstance();

    public static MemberService getInstance() {
        return memberService;
    }

    /**
     * 회원가입
     */

    public void signUp(Member member) {
        memberRepository.insert(member);
    }

    /**
     * 로그인
     */
    public Member login(String memberId, String password) {
        Optional<Member> loginMember = memberRepository.findByMemberId(memberId);
        if (loginMember.isEmpty())
            throw new LoginException("아이디가 존재하지 않습니다.");

        if (!loginMember.get().getPassword().equals(password))
            throw new LoginException("비밀번호가 틀립니다.");

        return loginMember.get();
    }

}
