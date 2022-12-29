package com.codestates.pre027.PreProjectStackOverFlow.question.repository;

import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTitleContainingOrTextContaining(String title, String text, Pageable pageable);
    List<Question> findByTitleContaining(String search);
    @Query(value = "SELECT r FROM QUESTIONS r WHERE MEMBER_ID = :num")
    List<Question> findByMember(@Param("num") long memberId);
}
