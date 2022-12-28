package com.codestates.pre027.PreProjectStackOverFlow.rating.controller;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.rating.dto.RatingDto;
import com.codestates.pre027.PreProjectStackOverFlow.rating.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RatingController {
    private final RatingService ratingService;
    private final JwtTokenizer jwtTokenizer;

    public RatingController(RatingService ratingService, JwtTokenizer jwtTokenizer) {
        this.ratingService = ratingService;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/questions/{question-id}/upratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto.QuestionResponse postQuestionUpRating(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("question-id") long questionId) {

        return ratingService.saveQuestionRating(
            questionId, jwtTokenizer.getMemberId(token), 1);
    }

    @PostMapping("/questions/{question-id}/downratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto.QuestionResponse postQuestionDownRating(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("question-id") long questionId) {

        return ratingService.saveQuestionRating(
            questionId, jwtTokenizer.getMemberId(token), -1);
    }

    @PostMapping("/answers/{answer-id}/upratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto.AnswerResponse postAnswerUpRating(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("answer-id") long answerId) {

        return ratingService.saveAnswerRating(
            answerId, jwtTokenizer.getMemberId(token), 1);
    }

    @PostMapping("/answers/{answer-id}/downratings")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto.AnswerResponse postAnswerDownRating(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("answer-id") long answerId) {

        return ratingService.saveAnswerRating(
            answerId, jwtTokenizer.getMemberId(token), -1);
    }
}
