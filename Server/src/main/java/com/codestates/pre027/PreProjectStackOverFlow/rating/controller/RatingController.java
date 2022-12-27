package com.codestates.pre027.PreProjectStackOverFlow.rating.controller;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.rating.dto.RatingDto;
import com.codestates.pre027.PreProjectStackOverFlow.rating.entity.Rating;
import com.codestates.pre027.PreProjectStackOverFlow.rating.mapper.RatingMapper;
import com.codestates.pre027.PreProjectStackOverFlow.rating.service.RatingService;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/ratings")
public class RatingController {
    private final JwtTokenizer jwtTokenizer;
    private final RatingMapper ratingMapper;
    private final RatingService ratingService;
    private final MemberService memberService;
    private final QuestionService questionService;

    public RatingController(JwtTokenizer jwtTokenizer, RatingMapper ratingMapper,
        RatingService ratingService, MemberService memberService, QuestionService questionService) {
        this.jwtTokenizer = jwtTokenizer;
        this.ratingMapper = ratingMapper;
        this.ratingService = ratingService;
        this.memberService = memberService;
        this.questionService = questionService;
    }

//    @PostMapping("/questions")
//    public ResponseEntity postQuestionRating(@RequestHeader(name = "Authorization") String token,
//        @RequestBody RatingDto.QuestionPost requestBody) {
//        Rating rating = ratingMapper.QuestionRatingPostDtoToRating(requestBody);
//        Rating createdRating = ratingService.createQuestionRating(rating,
//            requestBody.getQuestionId(), jwtTokenizer.getMemberId(token));
//        RatingDto.QuestionResponse response = ratingMapper.RatingToQuestionRatingResponseDto(createdRating);
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

//    @PatchMapping("/{question-id}")
//    public ResponseEntity patchQuestionRating(@RequestHeader(name = "Authorization") String token,
//        @PathVariable("question-id") @Positive long questionId,
//        @RequestBody RatingDto.QuestionPatch requestBody) {
//        requestBody.setQuestionId(questionId);
//
//        Rating rating = ratingMapper.QuestionRatingPatchDtoToRating(requestBody);
//        Rating updatedQuestionRating = ratingService.updateQuestionRating(rating, questionId, jwtTokenizer.getMemberId(token));
//        RatingDto.QuestionResponse response = ratingMapper.RatingToQuestionRatingResponseDto(updatedQuestionRating);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
