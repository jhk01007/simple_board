package org.example.recipe_board_jpa.repository;



import org.example.recipe_board_jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

//    int insert(Member member);
//    List<Member> findAll();
//    Optional<Member> findById(Long id);
    Optional<Member> findByServiceId(String serviceId);


}
