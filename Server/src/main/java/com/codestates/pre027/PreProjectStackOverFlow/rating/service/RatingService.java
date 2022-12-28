package com.codestates.pre027.PreProjectStackOverFlow.rating.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.repository.AnswerRepository;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.repository.QuestionRepository;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.rating.dto.RatingDto;
import com.codestates.pre027.PreProjectStackOverFlow.rating.entity.Rating;
import com.codestates.pre027.PreProjectStackOverFlow.rating.repository.RatingRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public RatingService(RatingRepository ratingRepository, MemberService memberService,
        QuestionService questionService, AnswerService answerService,
        QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.ratingRepository = ratingRepository;
        this.memberService = memberService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public RatingDto.QuestionResponse saveQuestionRating(long questionId, long memberId, int amount) {
        Question findQuestion = questionService.findVerifiedQuestion(questionId);
        Member findMember = memberService.findVerifiedMember(memberId);
        List<Rating> ratingList = ratingRepository.findAllByMemberAndQuestion(findMember, findQuestion);

        if(ratingList.isEmpty()) {
            Rating createdRating = createRating(findQuestion, findMember, amount);
            ratingRepository.save(createdRating);
            //score라는 값을 뽑아서 저장하고, 각 Reposit에 저장
            int score = ratingRepository.findAllByQuestion(findQuestion)
                .stream()
                .mapToInt(Rating::getAmount)
                .sum();
            findQuestion.setRatingScore(score);
            questionRepository.save(findQuestion);

            return RatingDto.QuestionResponse.builder()
                .memberId(memberId)
                .ratingScore(score)
                .upRating(amount == 1)
                .downRating(amount == -1)
                .questionId(questionId)
                .build();
        }
        else {
            ratingRepository.deleteAll(ratingList);
            int score = ratingRepository.findAllByQuestion(findQuestion)
                .stream()
                .mapToInt(Rating::getAmount)
                .sum();
            findQuestion.setRatingScore(score);
            questionRepository.save(findQuestion);
            return RatingDto.QuestionResponse.builder()
                .memberId(memberId)
                .ratingScore(score)
                .upRating(false)
                .downRating(false)
                .questionId(questionId)
                .build();
        }
    }

    public RatingDto.AnswerResponse saveAnswerRating(long answerId, long memberId, int amount) {
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        Member findMember = memberService.findVerifiedMember(memberId);
        List<Rating> ratingList = ratingRepository.findAllByMemberAndAnswer(findMember, findAnswer);

        if(ratingList.isEmpty()) {
            Rating createdRating = createRating(findAnswer, findMember, amount);
            ratingRepository.save(createdRating);
            int score = ratingRepository.findAllByAnswer(findAnswer)
                .stream()
                .mapToInt(Rating::getAmount)
                .sum();
            findAnswer.setRatingScore(score);
            answerRepository.save(findAnswer);
            return RatingDto.AnswerResponse.builder()
                .memberId(memberId)
                .ratingScore(score)
                .upRating(amount == 1)
                .downRating(amount == -1)
                .answerId(answerId)
                .build();
        }
        else {
            ratingRepository.deleteAll(ratingList);
            int score = ratingRepository.findAllByAnswer(findAnswer)
                .stream()
                .mapToInt(Rating::getAmount)
                .sum();
            findAnswer.setRatingScore(score);
            answerRepository.save(findAnswer);
            return RatingDto.AnswerResponse.builder()
                .ratingScore(score)
                .upRating(false)
                .downRating(false)
                .answerId(answerId)
                .memberId(memberId)
                .build();
        }
    }

    public Rating createRating(Question question, Member member, int amount) {
        return Rating.builder()
            .member(member)
            .question(question)
            .amount(amount)
            .build();
    }

    public Rating createRating(Answer answer, Member member, int amount) {
        return Rating.builder()
            .member(member)
            .answer(answer)
            .amount(amount)
            .build();
    }
}
