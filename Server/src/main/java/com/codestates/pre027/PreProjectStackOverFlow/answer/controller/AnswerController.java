package com.codestates.pre027.PreProjectStackOverFlow.answer.controller;

import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto;
import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.mapper.AnswerMapper;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService, AnswerMapper answerMapper){
        this.answerMapper = answerMapper;
        this.answerService = answerService;
    }

    @PostMapping("/questions/{quest-id}/answers")
    public ResponseEntity postAnswer(@PathVariable("quest-id") @Positive long questId,
        @Valid @RequestBody AnswerDto.Post answerPostDto){
        Answer answer = answerService.createAnswer(answerMapper.answerPostDto_to_Answer(answerPostDto),
            questId);
        return new ResponseEntity<>(
                answerPostDto,
                HttpStatus.CREATED);
    }

    @PatchMapping("/answers/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
        @Valid @RequestBody AnswerDto.Patch answerPatchDto){
        return new ResponseEntity(
            answerPatchDto,
            HttpStatus.OK);
    }

    @GetMapping("/answers/{answer-id}")
    public ResponseEntity getAnswer(@PathVariable("answer-id") @Positive long answerId){
        return new ResponseEntity(
            HttpStatus.OK);
    }

    @DeleteMapping("/answers/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
