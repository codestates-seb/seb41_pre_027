package com.codestates.pre027.PreProjectStackOverFlow.comment.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import com.codestates.pre027.PreProjectStackOverFlow.comment.repository.CommentRepository;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    public CommentService(CommentRepository commentRepository,
        QuestionService questionService,
        AnswerService answerService,
        MemberService memberService){
        this.commentRepository = commentRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.memberService = memberService;
    }

    public Comment createCommentToQuestion(Comment comment, long questId, long tokenId){
        Member member = memberService.findMember(tokenId);
        Question question = questionService.findQuestion(questId);

        comment.setInheritQuest(true);
        comment.setQuest(question);
        comment.setAnswer(null);
        comment.setWriter(member);

        return commentRepository.save(comment);
    }

    public Comment createCommentToAnswer(Comment comment, long answerId, long tokenId){
        Member member = memberService.findMember(tokenId);
        Answer answer = answerService.findAnswer(answerId);

        comment.setInheritQuest(false);
        comment.setQuest(null);
        comment.setAnswer(answer);
        comment.setWriter(member);

        return commentRepository.save(comment);
    }

    public void deleteComment(long commentId,long tokenId){
        Comment findComment = findComment(commentId);
        Member findMember = findComment.getWriter();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }
        commentRepository.delete(findComment);
    }

    public Comment findComment(long commentId){
        return findVerifiedCommentByQuery(commentId);
    }

    private Comment findVerifiedCommentByQuery(long commentId){
        Optional<Comment> optionalComment = commentRepository.findByComment(commentId);
        Comment findComment = optionalComment.orElseThrow(()->new BusinessLogicException(
            ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

    public List<Comment> findCommentByQuestionId(long questId){
        Question question = questionService.findQuestion(questId);
        return commentRepository.findByQuest(questId);
    }

    public List<Comment> findCommentByAnswerId(long answerId){
        Answer answer = answerService.findAnswer(answerId);
        return commentRepository.findByAnswer(answerId);
    }



}
