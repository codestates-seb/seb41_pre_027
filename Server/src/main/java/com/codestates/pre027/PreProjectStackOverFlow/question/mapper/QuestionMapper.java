package com.codestates.pre027.PreProjectStackOverFlow.question.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto.Response;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.QuestionTag;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    // QuestionDto.Post 를 Question 로 변환
    Question questionPostDtoToQuestion(QuestionDto.Post requestBody);
    Question questionPatchDtoToQuestion(QuestionDto.Patch requestBody);
    /*@Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "member.name", target = "name")
    @Mapping(source = "member.memberImage", target = "memberImage")
    QuestionDto.Response questionToQuestionResponseDto(Question question);*/
    List<QuestionDto.Response> questionsToQuestionResponseDtos(List<Question> questions);

    default QuestionDto.Response questionToQuestionResponseDto(Question question){
        StringBuilder sb = new StringBuilder();
        List<QuestionTag> questionTags = question.getQuestionTags();
        for(int i =0; i<questionTags.size(); i++){
            QuestionTag questionTag = questionTags.get(i);
            sb.append(questionTag.getTag().getTagName());
            if(i!=questionTags.size()-1) sb.append(",");
        }
        String tagString = sb.toString();
        QuestionDto.Response questionResponseDto = new Response(
            question.getQuestionId(),
            question.getTitle(),
            question.getText(),
            question.getMember().getMemberId(),
            question.getMember().getMemberImage(),
            question.getMember().getName(),
            question.getViews(),
            question.getRatingScore(),
            question.getCreatedAt(),
            question.getModifiedAt(),
            tagString
        );
        return questionResponseDto;
    }
}
