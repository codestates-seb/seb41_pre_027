package com.codestates.pre027.PreProjectStackOverFlow.answer.controller;

import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto;
import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.mapper.AnswerMapper;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final JwtTokenizer jwtTokenizer;

    public AnswerController(AnswerService answerService, AnswerMapper answerMapper,JwtTokenizer jwtTokenizer){
        this.answerMapper = answerMapper;
        this.answerService = answerService;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/questions/{quest-id}/answers")
    public ResponseEntity postAnswer(@RequestHeader(name = "Authorization") String token,
        @PathVariable("quest-id") @Positive long questId,
        @Valid @RequestBody AnswerDto.Post answerPostDto){
        Answer answer = answerService.createAnswer(answerMapper.answerPostDto_to_Answer(answerPostDto),
            questId,
            jwtTokenizer.getMemberId(token));
        AnswerDto.Response response = answerMapper.answer_to_AnswerResponseDto(answer);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @PatchMapping("/answers/{answer-id}")
    public ResponseEntity patchAnswer(@RequestHeader(name = "Authorization") String token,
        @PathVariable("answer-id") @Positive long answerId,
        @Valid @RequestBody AnswerDto.Patch answerPatchDto){
        answerPatchDto.setAnswerId(answerId);
        Answer answer = answerService.updateAnswer(answerMapper.answerPatchDto_to_Answer(answerPatchDto),
            jwtTokenizer.getMemberId(token));
        AnswerDto.Response response = answerMapper.answer_to_AnswerResponseDto(answer);
        return new ResponseEntity<>(
            response,
            HttpStatus.OK);
    }

    @GetMapping("/questions/{quest-id}/answers")
    public ResponseEntity getAnswersByQuestion(@PathVariable("quest-id") @Positive long questId){

        List<Answer> answers = answerService.findAnswersByQuestionId(questId);

        List<AnswerDto.Response> responses=
            answerMapper.answers_to_AnswerResponseDtos(answers);

        return new ResponseEntity<>(responses,
            HttpStatus.OK);
    }

    @GetMapping("/members/{member-id}/answers")
    public ResponseEntity getAnswersByMember(@PathVariable("member-id") @Positive long memberId){

        List<Answer> answers = answerService.findAnswersByMemberId(memberId);

        List<AnswerDto.Response> responses=
            answerMapper.answers_to_AnswerResponseDtos(answers);

        return new ResponseEntity<>(responses,
            HttpStatus.OK);
    }

    @DeleteMapping("/answers/{answer-id}")
    public ResponseEntity deleteAnswer(@RequestHeader(name = "Authorization") String token,
        @PathVariable("answer-id") @Positive long answerId){
        answerService.deleteAnswer(answerId,jwtTokenizer.getMemberId(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
