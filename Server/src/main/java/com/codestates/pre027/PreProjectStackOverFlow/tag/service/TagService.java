package com.codestates.pre027.PreProjectStackOverFlow.tag.service;

import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.exception.BusinessLogicException;
import com.codestates.pre027.PreProjectStackOverFlow.exception.ExceptionCode;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.repository.QuestionRepository;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.QuestionTag;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.Tag;
import com.codestates.pre027.PreProjectStackOverFlow.tag.repository.QuestionTagRepository;
import com.codestates.pre027.PreProjectStackOverFlow.tag.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;
    private final QuestionRepository questionRepository;

    public TagService(TagRepository tagRepository,
        QuestionTagRepository questionTagRepository,
        QuestionService questionService,
        AnswerService answerService,
        MemberService memberService,
        QuestionRepository questionRepository){
        this.tagRepository =tagRepository;
        this.questionTagRepository = questionTagRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.memberService = memberService;
        this.questionRepository = questionRepository;
    }

    //Tag 생성, 초기화, 업데이트를 전부 담당하는 메서드
    @Transactional
    public List<Tag> createTags(List<Tag> tags,long questId, long tokenId){
        Question findQuestion = questionService.findQuestion(questId);
        Member findMember = findQuestion.getMember();

        //우선 접근한 Question의 작성자와 현재 로그인한 유저가 같은지 검증
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }

        //원래 존재하던 QuestionId에 해당하는 QuestionTag를 다 지우고 시작함
        List<QuestionTag> findQuestionTags = findQuestion.getQuestionTags();
        List<Long> deleteId = new ArrayList<>();

        //QustionTags에 접근하여 QuestionId 같은 데이터 전부 삭제 (초기화)
        for(int i =0; i<findQuestionTags.size();i++) {
            QuestionTag questionTag = findQuestionTags.get(i);
            if(questionTag.getQuestion().getQuestionId()==questId){
                //System.out.println("삭제함때려?");
                Tag temp = questionTag.getTag();
                //System.out.println(temp.getTagId());
                //System.out.println(questionTag.getQuestionTagId());
                deleteId.add(questionTag.getQuestionTagId());
            }
        }
        //System.out.println(deleteId.toString());

        //deleteId에 있는 Id값대로 전부 제거
        questionTagRepository.deleteAllByIdInBatch(deleteId);

        //Tags에 들어온 tag값대로 접근
        for(int i =0; i<tags.size();i++){
            Tag tag = tags.get(i);
            Tag findTag = findVerifiedTagByQuery(tag.getTagName());

            //이미 존재하는 태그가 아닌경우
            if(findTag ==null) {
                QuestionTag questionTag = new QuestionTag();
                questionTag.setTag(tag);
                questionTag.setQuestion(findQuestion);
                tag.getQuestionTags().add(questionTag);
                tagRepository.save(tag);
            }else{ //이미 존재하는 태그인경우
                //System.out.println(findTag.getTagName()+"이미존재하는태그#########################");
                QuestionTag questionTag = new QuestionTag();
                questionTag.setTag(findTag);
                questionTag.setQuestion(findQuestion);
                long tagId = questionTag.getTag().getTagId();
                boolean chk = false;

                //이미존재하는 태그이면서, 같은 Question 에 연결된 태그인지 확인 ->큰 데이터가 됐을때 위험해질 가능성있음
                for(QuestionTag searchQuestionTag : findTag.getQuestionTags()){
                    if (searchQuestionTag.getTag().getTagId() == tagId
                        && searchQuestionTag.getQuestion().getQuestionId() == questId) {
                        chk = true;
                        break;
                    }
                }
                //그렇지 않으면 겹치지 않는 태그이므로, 이미 존재하는 태그를 새 Question에 연결
                if(!chk){
                    System.out.println("존재하는 태그인데 안겹침");
                    findTag.getQuestionTags().add(questionTag);
                }
                tagRepository.save(findTag);
            }

        }
        return tags;
    }

    public Page<Tag> findTags(Pageable pageable){
        List<Tag> tags = tagRepository.findAll();

        Page<Tag> tagPage = new PageImpl<>(tags,pageable,tags.size());

        return tagPage;

    }

    public Page<Question> findQuestionByTag(Pageable pageable,String tagName){
        Tag findTag = findVerifiedTag(tagName);
        List<QuestionTag> questionTags = findTag.getQuestionTags();
        List<Long> questionIdList = new ArrayList<>();
        for(QuestionTag questionTag:questionTags){
            Long questionId = questionTag.getQuestion().getQuestionId();
            questionIdList.add(questionId);
        }
        List<Question> questions = questionRepository.findAllById(questionIdList);

        Page<Question> questionPage = new PageImpl<>(questions,pageable,questions.size());

        return questionPage;

    }
    public Tag findVerifiedTag(long tagId){
        Optional<Tag> optionalQuestion = tagRepository.findById(tagId);
        Tag findTag = optionalQuestion.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND)
        );
        return findTag;
    }
    public Tag findVerifiedTag(String tagName){
        Tag findTag = findVerifiedTagByQuery(tagName);
        if(findTag==null) throw new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND);
        else return findTag;
    }

    private Tag findVerifiedTagByQuery(String tagName){
        Optional<Tag> tag = tagRepository.findByTag(tagName);
        if(tag.isPresent()){
            //System.out.println("있음######################################################");
            return tag.get();
        }
        else return null;
    }
}
