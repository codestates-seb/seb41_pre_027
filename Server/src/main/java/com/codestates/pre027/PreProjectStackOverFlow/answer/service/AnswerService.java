package com.codestates.pre027.PreProjectStackOverFlow.answer.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer,long questId){
        /*//Member member = memberService.findMember(1L);
        //Question question = questionService.findQuestion(questId);
        Question question = new Question();

        answer.setQuestion(question);
        answer.setMember(member);*/
        return answerRepository.save(answer);
    }
}
