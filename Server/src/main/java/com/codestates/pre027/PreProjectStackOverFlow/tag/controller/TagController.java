package com.codestates.pre027.PreProjectStackOverFlow.tag.controller;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.tag.mapper.TagMapper;
import com.codestates.pre027.PreProjectStackOverFlow.tag.service.TagService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final JwtTokenizer jwtTokenizer;

    private TagController(TagService tagService, TagMapper tagMapper, JwtTokenizer jwtTokenizer){
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    /*@PostMapping("/questions/{quest-id}/tags")
    public ResponseEntity postTags(@RequestHeader(name = "Authorization") String token,
        @PathVariable("quest-id") @Positive long questId){
        //@Valid @RequestBody AnswerDto.Post answerPostDto){
        Answer answer = answerService.createAnswer(answerMapper.answerPostDto_to_Answer(answerPostDto),
            questId,
            jwtTokenizer.getMemberId(token));
        AnswerDto.Response response = answerMapper.answer_to_AnswerResponseDto(answer);



        return new ResponseEntity<>(
            response,
            HttpStatus.CREATED);
    }*/
}
