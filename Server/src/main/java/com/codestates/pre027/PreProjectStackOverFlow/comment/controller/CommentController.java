package com.codestates.pre027.PreProjectStackOverFlow.comment.controller;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto;
import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import com.codestates.pre027.PreProjectStackOverFlow.comment.mapper.CommentMapper;
import com.codestates.pre027.PreProjectStackOverFlow.comment.service.CommentService;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final JwtTokenizer jwtTokenizer;

    public CommentController(CommentService commentService,
        CommentMapper commentMapper,
        JwtTokenizer jwtTokenizer){
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/questions/{quest-id}/comments")
    public ResponseEntity postCommentToQuestion(@RequestHeader(name = "Authorization") String token,
        @PathVariable("quest-id") @Positive long questId,
        @Valid @RequestBody CommentDto.Post commentPostDto){
        Comment comment = commentService.createCommentToQuestion(
            commentMapper.commentPostDto_to_Comment(commentPostDto),
            questId,jwtTokenizer.getMemberId(token));
        CommentDto.Response response = commentMapper.comment_to_CommentResponseDto(comment);
        return new ResponseEntity<>(
            response,
            HttpStatus.CREATED);
    }
}
