package com.codestates.pre027.PreProjectStackOverFlow.member.repository;


import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}
