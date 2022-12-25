package com.codestates.pre027.PreProjectStackOverFlow.question.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    // QuestionDto.Post 를 Question 로 변환
    Question questionPostDtoToQuestion(QuestionDto.Post requestBody);
    Question questionPatchDtoToQuestion(QuestionDto.Patch requestBody);
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "member.name", target = "name")
    QuestionDto.Response questionToQuestionResponseDto(Question question);
    List<QuestionDto.Response> questionsToQuestionResponseDtos(List<Question> questions);
}





