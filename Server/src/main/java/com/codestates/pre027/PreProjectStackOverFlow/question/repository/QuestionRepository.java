package com.codestates.pre027.PreProjectStackOverFlow.question.repository;

import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTitleContaining(String search);
}
