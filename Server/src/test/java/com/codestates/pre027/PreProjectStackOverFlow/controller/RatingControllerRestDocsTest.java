package com.codestates.pre027.PreProjectStackOverFlow.controller;

import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.mapper.QuestionMapper;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.rating.controller.RatingController;
import com.codestates.pre027.PreProjectStackOverFlow.rating.dto.RatingDto;
import com.codestates.pre027.PreProjectStackOverFlow.rating.entity.Rating;
import com.codestates.pre027.PreProjectStackOverFlow.rating.service.RatingService;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(value = RatingController.class , excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class RatingControllerRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private Gson gson;

    @Test
    public void postQuestionUpRatingTest() throws Exception {

        long questionId = 1L;

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        Member member = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);

        Question question = new Question(1L, "제목1", "내용1", 0 , 0, createdAt, modifiedAt);

        question.setMember(member);

        Rating rating = new Rating(1L,member,question,1);

        RatingDto.QuestionResponse responseDto =
            new RatingDto.QuestionResponse(1L,
                1L,
                1,
                true,
                false);

        given(ratingService.saveQuestionRating(Mockito.anyLong(),Mockito.anyLong(),Mockito.anyInt())).willReturn(responseDto);

        ResultActions actions =
            mockMvc.perform(
                RestDocumentationRequestBuilders.post("/api/questions/{question-id}/upratings", questionId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            );

        actions
            .andExpect(status().isCreated())
            .andDo(document(
                "question-rating-up",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("question-id").description("게시글 식별자")
                ),
                responseFields(
                    fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                    fieldWithPath("upRating").type(JsonFieldType.BOOLEAN).description("추천을 눌렀는지 확인하는 값"),
                    fieldWithPath("downRating").type(JsonFieldType.BOOLEAN).description("비추천을 눌렀는지 확인하는 값")
                )
            ));
    }
}
