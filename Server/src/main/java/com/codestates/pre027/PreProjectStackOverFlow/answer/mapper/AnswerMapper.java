package com.codestates.pre027.PreProjectStackOverFlow.answer.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto;
import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto.Response;
import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerPostDto_to_Answer(AnswerDto.Post answerPostDto);
    Answer answerPatchDto_to_Answer(AnswerDto.Patch answerPatchDto);

    List<Response> answers_to_AnswerResponseDtos(List<Answer> answers);
    default AnswerDto.Response answer_to_AnswerResponseDto(Answer answer){
        AnswerDto.Response answerResponseDto = new AnswerDto.Response(answer.getQuest().getQuestionId(),
            answer.getAnswerId(),
            answer.getWriter().getMemberId(),
            answer.getWriter().getName(),
            answer.getText(),
            answer.getCreatedAt(),
            answer.getModifiedAt()
            );
        return answerResponseDto;
    }


}
