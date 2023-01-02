package com.codestates.pre027.PreProjectStackOverFlow.exception;

import lombok.Getter;

// ExceptionCode 설정
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    TAG_NOT_FOUND(404, "Tag not found"),
    MEMBER_FORBIDDEN(403,"Member Forbidden"),
    MEMBER_UNAUTHORIZED(401,"UNAUTHORIZED");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}