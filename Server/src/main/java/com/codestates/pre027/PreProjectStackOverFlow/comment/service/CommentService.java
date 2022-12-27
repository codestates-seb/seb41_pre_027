package com.codestates.pre027.PreProjectStackOverFlow.comment.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import com.codestates.pre027.PreProjectStackOverFlow.comment.repository.CommentRepository;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
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
}
