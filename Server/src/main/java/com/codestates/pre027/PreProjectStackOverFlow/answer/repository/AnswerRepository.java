package com.codestates.pre027.PreProjectStackOverFlow.answer.repository;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "SELECT c FROM Answer c WHERe c.answerId = :answerId")
    Optional<Answer> findByAnswer(long answerId);
}
