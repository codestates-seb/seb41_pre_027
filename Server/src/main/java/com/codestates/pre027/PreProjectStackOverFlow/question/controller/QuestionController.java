package com.codestates.pre027.PreProjectStackOverFlow.question.controller;


import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.dto.CountMultiResponseDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.mapper.QuestionMapper;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.tag.service.TagService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api/questions")
@Validated
public class QuestionController {

    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final JwtTokenizer jwtTokenizer;
    private final TagService tagService;

    public QuestionController(QuestionMapper questionMapper, QuestionService questionService,
        JwtTokenizer jwtTokenizer,TagService tagService) {
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.jwtTokenizer = jwtTokenizer;
        this.tagService = tagService;
    }

    @PostMapping("/posting")
    public ResponseEntity postQuestion(@RequestHeader(name = "Authorization") String token,
        @RequestBody QuestionDto.Post requestBody) {
        Question question = questionMapper.questionPostDtoToQuestion(requestBody);
        Question createdQuestion = questionService.createQuestion(question,
            jwtTokenizer.getMemberId(token));
        QuestionDto.Response response = questionMapper.questionToQuestionResponseDto(
            createdQuestion);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@RequestHeader(name = "Authorization") String token,
        @PathVariable("question-id") @Positive long questionId,
        @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);

        Question updatedQuestion = questionService.updateQuestion(
            questionMapper.questionPatchDtoToQuestion(requestBody),
            jwtTokenizer.getMemberId(token));

        QuestionDto.Response response = questionMapper.questionToQuestionResponseDto(
            updatedQuestion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive long questionId) {
        Question question = questionService.findQuestion(questionId);
        QuestionDto.Response response = questionMapper.questionToQuestionResponseDto(question);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(
        @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Question> questionPage = questionService.findQuestions(pageable);
        List<Question> questions = questionPage.getContent();
        long questionCount = questionService.findQuestionCount();

        return new ResponseEntity<>(
            new CountMultiResponseDto<>(
                questionMapper.questionsToQuestionResponseDtos(questions), questionCount),
            HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity getQuestionsByTag(@RequestParam String tagName,
        @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Question> questionPage = tagService.findQuestionByTag(pageable,tagName);
        List<Question> questions = questionPage.getContent();

        List<QuestionDto.Response> responses = questionMapper.questionsToQuestionResponseDtos(questions);

        return new ResponseEntity<>(responses,
            HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@RequestHeader(name = "Authorization") String token,
        @PathVariable("question-id") @Positive long questionId) {
        questionService.deleteQuestion(questionId, jwtTokenizer.getMemberId(token));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity searchQuestion(@RequestParam String search,
        @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Question> searchQuestionPage = questionService.searchQuestion(search, pageable);
        List<Question> searchQuestionList = searchQuestionPage.getContent();

        List<QuestionDto.Response> response = questionMapper.questionsToQuestionResponseDtos(
            searchQuestionList);

        long searchQuestionCount = searchQuestionList.size();

        return new ResponseEntity<>(new CountMultiResponseDto<>(
            response, searchQuestionCount
        ), HttpStatus.OK);
    }

    @GetMapping("/member/{member-id}")
    public ResponseEntity memberQuestionList(@PathVariable("member-id") @Positive long memberId,
        @PageableDefault(size = 10, sort = "questionId", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Question> memberQuestionList = questionService.memberQuestionList(memberId,pageable);
        List<QuestionDto.Response> response = questionMapper.questionsToQuestionResponseDtos(memberQuestionList);
        long count = response.size();

        return new ResponseEntity<>(new CountMultiResponseDto<>(
            response, count
        ), HttpStatus.OK);
    }
}
