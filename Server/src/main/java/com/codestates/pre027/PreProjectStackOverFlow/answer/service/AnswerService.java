package com.codestates.pre027.PreProjectStackOverFlow.answer.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.repository.AnswerRepository;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    private final QuestionService questionService;

    private final MemberService memberService;

    public AnswerService(AnswerRepository answerRepository,QuestionService questionService, MemberService memberService){
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.memberService = memberService;
    }

    public Answer createAnswer(Answer answer,long questId,long tokenId){
        Member member = memberService.findMember(tokenId);
        Question question = questionService.findQuestion(questId);

        answer.setQuest(question);
        answer.setWriter(member);

        return answerRepository.save(answer);
    }


    public Answer updateAnswer(Answer answer,long tokenId){
        Answer findAnswer = findVerifiedAnswerByQuery(answer.getAnswerId());
        Member findMember = findAnswer.getWriter();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }
        Optional.ofNullable(answer.getText())
            .ifPresent(text->findAnswer.setText(text));
        findAnswer.setModifiedAt(LocalDateTime.now());
        return answerRepository.save(findAnswer);
    }

    public List<Answer> findAnswersByQuestionId(long questId){
        Question question = questionService.findQuestion(questId);
        return answerRepository.findByQuest(question.getQuestionId());
    }

    public List<Answer> findAnswersByMemberId(long memberId){
        Member member = memberService.findMember(memberId);
        return answerRepository.findByWriter(member.getMemberId());
    }

    public void deleteAnswer(long answerId,long tokenId){
        Answer findAnswer = findAnswer(answerId);
        Member findMember = findAnswer.getWriter();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }

        answerRepository.delete(findAnswer);

    }

    public Answer findAnswer(long answerId){
        return findVerifiedAnswerByQuery(answerId);
    }

    private Answer findVerifiedAnswerByQuery(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findByAnswer(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(()->new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return findAnswer;
    }

    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND)
        );
        return findAnswer;
    }
}
