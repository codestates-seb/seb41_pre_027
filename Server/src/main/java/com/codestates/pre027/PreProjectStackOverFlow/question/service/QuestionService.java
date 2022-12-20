package com.codestates.pre027.PreProjectStackOverFlow.question.service;

import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.repository.QuestionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Question updateQuestion(Question question) {
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle())
            .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getText())
            .ifPresent(findQuestion::setText);

        findQuestion.setModifiedAt(LocalDateTime.now());

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(long questionId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        return findQuestion;
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
            Sort.by("questionId").descending()));
    }

    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND)
        );

        return findQuestion;
    }
}
