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
import com.codestates.pre027.PreProjectStackOverFlow.tag.repository.QuestionTagRepository;
import com.codestates.pre027.PreProjectStackOverFlow.tag.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    public TagService(TagRepository tagRepository,
        QuestionTagRepository questionTagRepository,
        QuestionService questionService,
        AnswerService answerService,
        MemberService memberService){
        this.tagRepository =tagRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.memberService = memberService;

    }
    @Transactional
    public List<Tag> createTags(List<Tag> tags,long questId, long tokenId){
        Question findQuestion = questionService.findQuestion(questId);
        Member findMember = findQuestion.getMember();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED);
        }
        //원래 존재하던 QuestionId에 해당하는 QuestionTag를 다 지우고 시작함
        List<QuestionTag> findQuestionTags = findQuestion.getQuestionTags();
        List<Long> deleteId = new ArrayList<>();
        for(int i =0; i<findQuestionTags.size();i++) {
            QuestionTag questionTag = findQuestionTags.get(i);
            if(questionTag.getQuestion().getQuestionId()==questId){
                System.out.println("삭제함때려?");
                Tag temp = questionTag.getTag();
                System.out.println(temp.getTagId());
                System.out.println(questionTag.getQuestionTagId());
                deleteId.add(questionTag.getQuestionTagId());
            }
        }
        System.out.println(deleteId.toString());
        questionTagRepository.deleteAllByIdInBatch(deleteId);
        for(int i =0; i<tags.size();i++){
            Tag tag = tags.get(i);
            Tag findTag = findVerifiedTagByQuery(tag.getTagName());
            if(findTag ==null) {
                QuestionTag questionTag = new QuestionTag();
                questionTag.setTag(tag);
                questionTag.setQuestion(findQuestion);
                tag.getQuestionTags().add(questionTag);
                tagRepository.save(tag);
            }else{
                System.out.println(findTag.getTagName()+"이미존재하는태그#########################");
                QuestionTag questionTag = new QuestionTag();
                questionTag.setTag(findTag);
                questionTag.setQuestion(findQuestion);
                long tagId = questionTag.getTag().getTagId();
                boolean chk = false;
                for(QuestionTag searchQuestionTag : findTag.getQuestionTags()){
                    if (searchQuestionTag.getTag().getTagId() == tagId
                        && searchQuestionTag.getQuestion().getQuestionId() == questId) {
                        chk = true;
                        break;
                    }
                }
                if(!chk){
                    System.out.println("존재하는 태그인데 안겹침");
                    findTag.getQuestionTags().add(questionTag);
                }
                tagRepository.save(findTag);
            }

        }
        return tags;
    }

    private Tag findVerifiedTagByQuery(String tagName){
        Optional<Tag> tag = tagRepository.findByTag(tagName);
        if(tag.isPresent()){
            System.out.println("있음######################################################");
            return tag.get();
        }
        else return null;
    }
}
