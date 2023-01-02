package com.codestates.pre027.PreProjectStackOverFlow.member.repository;

import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    // db 에서 email 조회
    Optional<Member> findByEmail(String email);

    // db 에서 search 로 들어오는 string 값 name 에서 pageable 조회
    Page<Member> findByNameContaining(String search , Pageable pageable);

    // db 에서 search 로 들어오는 string 값 name 에서 List 조회
    List<Member> findByNameContaining(String search);

}
