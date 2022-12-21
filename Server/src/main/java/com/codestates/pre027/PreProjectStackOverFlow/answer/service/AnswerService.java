package com.codestates.pre027.PreProjectStackOverFlow.answer.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.repository.AnswerRepository;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer,long questId){
        /*Member member = memberService.findMember(1L);
        Question question = questionService.findQuestion(questId);
        Question question = new Question();

        answer.setQuestion(question);
        answer.setMember(member);*/
        answer.setQuestionId(questId);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer){
        Answer findAnswer = findVerifiedAnswerByQuery(answer.getAnswerId());

        Optional.ofNullable(answer.getText())
            .ifPresent(text->findAnswer.setText(text));
        findAnswer.setModifiedAt(LocalDateTime.now());
        return answerRepository.save(findAnswer);
    }
    public Answer findAnswer(long answerId){
        return findVerifiedAnswerByQuery(answerId);
    }
    private Answer findVerifiedAnswerByQuery(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findByAnswer(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(()->new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return findAnswer;
    }
}
