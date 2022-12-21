package com.codestates.pre027.PreProjectStackOverFlow.answer.repository;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "SELECT c FROM ANSWERS c WHERE c.answerId = :answerId")
    Optional<Answer> findByAnswer(long answerId);

    @Query(value = "SELECT c FROM ANSWERS c WHERE QUEST_QUESTION_ID = :questId")
    List<Answer> findByQuest(long questId);
}
