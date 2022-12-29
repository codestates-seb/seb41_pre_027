package com.codestates.pre027.PreProjectStackOverFlow.controller;

import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codestates.pre027.PreProjectStackOverFlow.answer.controller.AnswerController;
import com.codestates.pre027.PreProjectStackOverFlow.answer.dto.AnswerDto;
import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.mapper.AnswerMapper;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
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
@AutoConfigureRestDocs(uriHost = "ec2-15-164-211-228.ap-northeast-2.compute.amazonaws.com")
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
                "DryLeonhard",
                "답변내용입니다",
                createdAt,
                modifiedAt,
                1L);


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
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자"),
                    fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정 일자"),
                    fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("회원 이미지 코드")
                )
            ));
    }

    @Test
    public void patchAnswerTest() throws Exception{
        long answerId = 1L;
        AnswerDto.Patch patch = new AnswerDto.Patch(answerId,"수정된 답변내용입니다");
        String content = gson.toJson(patch);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        AnswerDto.Response response =
            new AnswerDto.Response(1L,
                1L,
                1L,
                "DryLeonhard",
                "수정된 답변내용입니다",
                createdAt,
                modifiedAt,
                1L);

        given(answerMapper.answerPatchDto_to_Answer(Mockito.any(AnswerDto.Patch.class)))
            .willReturn(new Answer());

        given(answerService.updateAnswer(Mockito.any(Answer.class),
            Mockito.anyLong())).willReturn(new Answer());

        given(answerMapper.answer_to_AnswerResponseDto(Mockito.any(Answer.class)))
            .willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                patch("/api/answers/{answer-id}", answerId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.text").value(patch.getText()))
            .andDo(document(
                "patch-answer",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken")
                ),
                pathParameters(
                    parameterWithName("answer-id").description("답변 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("수정될 답변 식별자"),
                        fieldWithPath("text").type(JsonFieldType.STRING).description("수정된 답변 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문글 식별자"),
                    fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberNick").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("수정된 답변 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자"),
                    fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정 일자"),
                    fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("회원 이미지 코드")
                )
            ));
    }

    @Test
    public void getAnswersByQuestionTest() throws Exception{
        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Question question = new Question(1L,"질문 제목","질문 내용",0,0,createdAt,modifiedAt);

        Member member = new Member(1L,"dry@gmail.com","qwer1234","뜨륵이",1L);

        Answer answer1 = new Answer(1L,"답변 내용 1입니다",createdAt,modifiedAt,member,question,null);

        Answer answer2 = new Answer(2L, "답변 내용 2입니다",createdAt,modifiedAt,member,question,null);

        List<Answer> answers = List.of(answer1,answer2);

        given(answerService.findAnswersByQuestionId(Mockito.anyLong())).willReturn(answers);

        given(answerMapper.answers_to_AnswerResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new AnswerDto.Response(
                        answers.get(0).getQuest().getQuestionId(),
                        answers.get(0).getAnswerId(),
                        answers.get(0).getWriter().getMemberId(),
                        answers.get(0).getWriter().getName(),
                        answers.get(0).getText(),
                        answers.get(0).getCreatedAt(),
                        answers.get(0).getModifiedAt(),
                        answers.get(0).getWriter().getMemberImage()
                    ),
                    new AnswerDto.Response(
                        answers.get(1).getQuest().getQuestionId(),
                        answers.get(1).getAnswerId(),
                        answers.get(1).getWriter().getMemberId(),
                        answers.get(1).getWriter().getName(),
                        answers.get(1).getText(),
                        answers.get(1).getCreatedAt(),
                        answers.get(1).getModifiedAt(),
                        answers.get(1).getWriter().getMemberImage()
                    )
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/questions/{quest-id}/answers",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$[0].questionId").value(answers.get(0).getQuest().getQuestionId()))
            .andExpect(jsonPath("$[0].answerId").value(answers.get(0).getAnswerId()))
            .andExpect(jsonPath("$[0].memberId").value(answers.get(0).getWriter().getMemberId()))
            .andExpect(jsonPath("$[0].memberNick").value(answers.get(0).getWriter().getName()))
            .andExpect(jsonPath("$[0].text").value(answers.get(0).getText()))
            .andExpect(jsonPath("$[0].createdAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$[0].modifiedAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$[0].memberImage").value(answers.get(0).getWriter().getMemberImage()))
            .andDo(document("get-answers-by-question",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(parameterWithName("quest-id").description("조회할 질문 식별자")
                ),
                responseFields(
                    fieldWithPath("[].questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                    fieldWithPath("[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                    fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                    fieldWithPath("[].memberNick").type(JsonFieldType.STRING).description("작성자 이름"),
                    fieldWithPath("[].text").type(JsonFieldType.STRING).description("답변 내용"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("답변 생성일자"),
                    fieldWithPath("[].modifiedAt").type(JsonFieldType.STRING).description("답변 수정일자"),
                    fieldWithPath("[].memberImage").type(JsonFieldType.NUMBER).description("작성자 사진")
                )
            ));

    }

    @Test
    public void deleteAnswerTest() throws Exception{
        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);



        Question question = new Question(1L,"질문 제목","질문 내용",0,0,createdAt,modifiedAt);

        Member member = new Member(1L,"dry@gmail.com","qwer1234","뜨륵이",1L);

        Answer answer = new Answer(1L,"답변 내용입니다",createdAt,modifiedAt,member,question,null);

        answer.setQuest(question);
        answer.setWriter(member);

        doNothing().when(answerService).deleteAnswer(answer.getAnswerId(),1L);

        ResultActions resultActions = mockMvc.perform(
            delete("/api/answers/{answer-id}",answer.getAnswerId())
                .header("Authorization", "Bearer (accessToken)")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-answer",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    pathParameters(parameterWithName("answer-id").description("답변 식별자")
                    ),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                )
            );
    }
}
