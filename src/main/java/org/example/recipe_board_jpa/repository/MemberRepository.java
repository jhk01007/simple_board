package org.example.recipe_board_jpa.repository;



import org.apache.ibatis.annotations.Mapper;
import org.example.recipe_board_jpa.domain.Member;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {

    int insert(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Optional<Member> findByServiceId(String memberId);
}
