package com.codestates.pre027.PreProjectStackOverFlow.question.controller;


import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.mapper.QuestionMapper;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;

    public QuestionController(QuestionMapper questionMapper, QuestionService questionService) {
        this.questionMapper = questionMapper;
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Post requestBody) {
        Question question = questionMapper.questionPostDtoToQuestion(requestBody);

        Question createdQuestion = questionService.createQuestion(question);

        QuestionDto.Response response = questionMapper.questionToQuestionResponseDto(createdQuestion);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
