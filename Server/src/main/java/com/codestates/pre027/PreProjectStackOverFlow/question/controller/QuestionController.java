package com.codestates.pre027.PreProjectStackOverFlow.question.controller;


import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.dto.MultiResponseDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.mapper.QuestionMapper;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@Validated
public class QuestionController {
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final JwtTokenizer jwtTokenizer;

    public QuestionController(QuestionMapper questionMapper, QuestionService questionService,
        JwtTokenizer jwtTokenizer) {
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping
    public ResponseEntity postQuestion(@RequestHeader(name = "Authorization") String token,
        @RequestBody QuestionDto.Post requestBody) {
        Question question = questionMapper.questionPostDtoToQuestion(requestBody);

        Question createdQuestion = questionService.createQuestion(question,jwtTokenizer.getMemberId(token));

        QuestionDto.Response response = questionMapper.questionToQuestionResponseDto(createdQuestion);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@RequestHeader(name = "Authorization") String token,
        @PathVariable("question-id") @Positive long questionId,
        @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);

        Question updatedQuestion = questionService.updateQuestion(questionMapper.questionPatchDtoToQuestion(requestBody),
            jwtTokenizer.getMemberId(token));

        QuestionDto.Response response = questionMapper.questionToQuestionResponseDto(updatedQuestion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive long questionId) {
        Question question = questionService.findQuestion(questionId);
        QuestionDto.Response response= questionMapper.questionToQuestionResponseDto(question);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
        @Positive @RequestParam int size) {

        Page<Question> questionPage = questionService.findQuestions(page-1, size);

        List<Question> questions = questionPage.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(
            questionMapper.questionsToQuestionResponseDtos(questions), questionPage
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@RequestHeader(name = "Authorization") String token,
        @PathVariable("question-id") @Positive long questionId) {
        questionService.deleteQuestion(questionId, jwtTokenizer.getMemberId(token));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity searchQuestion(@RequestParam String search) {
        List<Question> searchQuestionList = questionService.searchQuestion(search);
        List<QuestionDto.Response> response = questionMapper.questionsToQuestionResponseDtos(searchQuestionList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
