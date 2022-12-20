package com.codestates.pre027.PreProjectStackOverFlow.question.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    // QuestionDto.Post 를 Question 로 변환
    Question questionPostDtoToQuestion(QuestionDto.Post requestBody);
    Question questionPatchDtoToQuestion(QuestionDto.Patch requestBody);
    QuestionDto.Response questionToQuestionResponseDto(Question question);
}
