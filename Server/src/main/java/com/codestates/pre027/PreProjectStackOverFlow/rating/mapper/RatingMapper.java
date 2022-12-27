package com.codestates.pre027.PreProjectStackOverFlow.rating.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.rating.dto.RatingDto;
import com.codestates.pre027.PreProjectStackOverFlow.rating.entity.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    Rating QuestionRatingPostDtoToRating(RatingDto.QuestionPost requestBody);
    Rating AnswerRatingPostDtoToRating(RatingDto.AnswerPost requestBody);
    Rating QuestionRatingPatchDtoToRating(RatingDto.QuestionPatch requestBody);
    Rating AnswerRatingPatchDtoToRating(RatingDto.AnswernPatch requestBody);

    RatingDto.QuestionResponse RatingToQuestionRatingResponseDto(Rating rating);
    RatingDto.AnswerResponse RatingToAnswerRatingResponseDto(Rating rating);
}
