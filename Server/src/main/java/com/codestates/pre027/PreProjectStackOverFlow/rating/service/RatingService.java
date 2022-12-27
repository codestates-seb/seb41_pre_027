package com.codestates.pre027.PreProjectStackOverFlow.rating.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.rating.entity.Rating;
import com.codestates.pre027.PreProjectStackOverFlow.rating.repository.RatingRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MemberService memberService;
//    private final QuestionService questionService;
//    private final AnswerService answerService;

    public Rating createQuestionRating() {
        return ratingRepository.save(new Rating());
    }

//    public Rating updateQuestionRating(Rating rating, long questionId,  long tokenId) {
//        Question findQuestion = questionService.findVerifiedQuestion(questionId);
//        Member findMember = memberService.findMember(tokenId);
//        return new Rating();
//    }
//
//    public Rating updateAnswerRating(Rating rating, long tokenId) {
//
//    }
//
//    public Rating findRating(long ratingId) {
//        Rating findRating = findVerifiedMemberRating(ratingId);
//    }
}
