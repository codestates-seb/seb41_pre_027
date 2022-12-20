package com.codestates.pre027.PreProjectStackOverFlow.question.service;

import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }
}
