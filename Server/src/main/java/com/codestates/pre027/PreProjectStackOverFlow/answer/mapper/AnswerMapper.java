package com.codestates.pre027.PreProjectStackOverFlow.answer.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto;
import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerPostDto_to_Answer(AnswerDto.Post answerPostDto);
    Answer answerPatchDto_to_Answer(AnswerDto.Patch answerPatchDto);

}
