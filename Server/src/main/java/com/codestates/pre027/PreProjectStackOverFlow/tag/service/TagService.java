package com.codestates.pre027.PreProjectStackOverFlow.tag.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.QuestionTag;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.Tag;
import com.codestates.pre027.PreProjectStackOverFlow.tag.repository.TagRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    public TagService(TagRepository tagRepository,
        QuestionService questionService,
        AnswerService answerService,
        MemberService memberService){
        this.tagRepository =tagRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.memberService = memberService;
    }
    public List<Tag> createTags(List<Tag> tags,long questId, long tokenId){
        Question findQuestion = questionService.findQuestion(questId);
        Member findMember = findQuestion.getMember();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }
        for(Tag tag:tags){
            QuestionTag questionTag = new QuestionTag();
            questionTag.setTag(tag);
            questionTag.setQuestion(findQuestion);
            tag.getQuestionTags().add(questionTag);
        }
        return tagRepository.saveAll(tags);

    }
}
