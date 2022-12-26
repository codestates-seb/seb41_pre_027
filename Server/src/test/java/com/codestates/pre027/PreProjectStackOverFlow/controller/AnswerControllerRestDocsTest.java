package com.codestates.pre027.PreProjectStackOverFlow.controller;

import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codestates.pre027.PreProjectStackOverFlow.answer.controller.AnswerController;
import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto;
import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.mapper.AnswerMapper;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = AnswerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private AnswerMapper answerMapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private MemberService memberService;
    @MockBean
    private QuestionService questionService;

    @Autowired
    private Gson gson;

    @Test
    public void postAnswerTest() throws Exception{

        long questionId = 1L;

        AnswerDto.Post post = new AnswerDto.Post("답변내용입니다");
        //post.setText();
        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        AnswerDto.Response response =
            new AnswerDto.Response(1L,
                1L,
                1L,
                "oheadnah",
                "답변내용입니다",
                createdAt,
                modifiedAt);


        given(answerMapper.answerPostDto_to_Answer(Mockito.any(AnswerDto.Post.class)))
            .willReturn(new Answer());

        given(answerService.createAnswer(Mockito.any(Answer.class),
            Mockito.anyLong(),Mockito.anyLong())).willReturn(new Answer());

        given(answerMapper.answer_to_AnswerResponseDto(Mockito.any(Answer.class)))
            .willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                post("/api/questions/{quest-id}/answers",questionId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.text").value(post.getText()))
            .andDo(document(
                "post-answer",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("text").type(JsonFieldType.STRING).description("답변 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                    fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberNick").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("답변 내용"),
                    fieldWithPath("createdAt").ignored(),
                    fieldWithPath("modifiedAt").ignored()
                )
            ));


    }

}
