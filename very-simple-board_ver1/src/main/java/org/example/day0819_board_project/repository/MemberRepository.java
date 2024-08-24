package org.example.day0819_board_project.repository;

import org.example.day0819_board_project.domain.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    int insert(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByMemberId(String memberId);
}
