package org.example.board.mvc.repository;



import org.apache.ibatis.annotations.Mapper;
import org.example.board.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    int insert(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByMemberId(String memberId);
}
