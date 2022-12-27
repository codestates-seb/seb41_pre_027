package com.codestates.pre027.PreProjectStackOverFlow.comment.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto;
import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto.Response;
import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDto_to_Comment(CommentDto.Post commentPostDto);
    default CommentDto.Response comment_to_CommentResponseDto(Comment comment){
        Response commentResponseDto;
        if(comment.isInheritQuest()) {
            commentResponseDto = new Response(
                comment.getQuest().getQuestionId(),
                comment.getCommentId(),
                comment.getWriter().getMemberId(),
                comment.getWriter().getName(),
                comment.getText(),
                comment.getCreatedAt()
            );
        }else{
            commentResponseDto = new Response(
                comment.getAnswer().getAnswerId(),
                comment.getCommentId(),
                comment.getWriter().getMemberId(),
                comment.getWriter().getName(),
                comment.getText(),
                comment.getCreatedAt()
            );
        }
        return commentResponseDto;
    }
}
