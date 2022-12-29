package com.codestates.pre027.PreProjectStackOverFlow.controller;

import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.codestates.pre027.PreProjectStackOverFlow.question.controller.QuestionController;
import com.codestates.pre027.PreProjectStackOverFlow.question.dto.QuestionDto;
import com.codestates.pre027.PreProjectStackOverFlow.question.entity.Question;
import com.codestates.pre027.PreProjectStackOverFlow.question.mapper.QuestionMapper;
import com.codestates.pre027.PreProjectStackOverFlow.question.service.QuestionService;
import com.codestates.pre027.PreProjectStackOverFlow.tag.service.TagService;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(value = QuestionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "ec2-15-164-211-228.ap-northeast-2.compute.amazonaws.com")
public class QuestionControllerRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private TagService tagService;

    @MockBean
    private QuestionMapper mapper;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private MemberMapper memberMapper;

    @MockBean
    private MemberService memberService;

    @Autowired
    private Gson gson;

    @Test
    public void postQuestionTest() throws Exception {

        QuestionDto.Post post = new QuestionDto.Post("제목입니다", "내용입니다");
        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        QuestionDto.Response responseDto =
            new QuestionDto.Response(1L,
                "제목입니다",
                "내용입니다",
                1L,
                1,
                "oheadnah",
                0,
                0,
                createdAt,
                modifiedAt,
                0,
                "");

        given(mapper.questionPostDtoToQuestion(Mockito.any(QuestionDto.Post.class))).willReturn(
            new Question());

        given(questionService.createQuestion(Mockito.any(Question.class),
            Mockito.anyLong())).willReturn(new Question());

        given(mapper.questionToQuestionResponseDto(Mockito.any(Question.class))).willReturn(
            responseDto);

        ResultActions actions =
            mockMvc.perform(
                post("/api/questions/posting")
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(post.getTitle()))
            .andExpect(jsonPath("$.text").value(post.getText()))
            .andDo(document(
                "post-question",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken)")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                        fieldWithPath("text").type(JsonFieldType.STRING).description("게시글 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("회원 이미지"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회 수"),
                    fieldWithPath("ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                    fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정일"),
                    fieldWithPath("answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                    fieldWithPath("tagString").type(JsonFieldType.STRING).description("태그")
                )
            ));
    }

    @Test
    public void patchQuestionTest() throws Exception {

        long questionId = 1L;
        QuestionDto.Patch patch = new QuestionDto.Patch(questionId, "제목수정입니다", "내용수정입니다");
        String content = gson.toJson(patch);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        QuestionDto.Response responseDto =
            new QuestionDto.Response(1L,
                "제목수정입니다",
                "내용수정입니다",
                1L,
                1,
                "oheadnah",
                0,
                0,
                createdAt,
                modifiedAt,
                0,
                "");

        given(mapper.questionPatchDtoToQuestion(Mockito.any(QuestionDto.Patch.class))).willReturn(
            new Question());

        given(questionService.updateQuestion(Mockito.any(Question.class), Mockito.anyLong())).willReturn(
            new Question());

        given(mapper.questionToQuestionResponseDto(Mockito.any(Question.class))).willReturn(responseDto);


        ResultActions actions =
            mockMvc.perform(
                patch("/api/questions/{question-id}", questionId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );


        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.questionId").value(patch.getQuestionId()))
            .andExpect(jsonPath("$.title").value(patch.getTitle()))
            .andExpect(jsonPath("$.text").value(patch.getText()))
            .andDo(document("patch-question",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("question-id").description("게시글 식별자")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken)")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("게시글 식별자")
                            .ignored(),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목")
                            .optional(),
                        fieldWithPath("text").type(JsonFieldType.STRING).description("게시글 내용")
                            .optional()
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                        fieldWithPath("text").type(JsonFieldType.STRING).description("게시글 내용"),
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("회원 이미지"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                        fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회 수"),
                        fieldWithPath("ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정일"),
                        fieldWithPath("answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                        fieldWithPath("tagString").type(JsonFieldType.STRING).description("태그")
                    )
                )
            ));
    }

    @Test
    public void getQuestionTest() throws Exception {

        long questionId = 1L;

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        QuestionDto.Response responseDto =
            new QuestionDto.Response(1L,
                "제목입니다",
                "내용입니다",
                1L,
                1,
                "oheadnah",
                0,
                0,
                createdAt,
                modifiedAt,
                0,
                "");

        given(questionService.findQuestion(Mockito.anyLong())).willReturn(new Question());

        given(mapper.questionToQuestionResponseDto(Mockito.any(Question.class))).willReturn(responseDto);

        ResultActions resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/questions/{question-id}", questionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.questionId").value(responseDto.getQuestionId()))
            .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
            .andExpect(jsonPath("$.text").value(responseDto.getText()))
            .andExpect(jsonPath("$.memberId").value(responseDto.getMemberId()))
            .andExpect(jsonPath("$.memberImage").value(responseDto.getMemberImage()))
            .andExpect(jsonPath("$.name").value(responseDto.getName()))
            .andExpect(jsonPath("$.views").value(responseDto.getViews()))
            .andExpect(jsonPath("$.ratingScore").value(responseDto.getRatingScore()))
            .andDo(document("get-question",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("question-id").description("게시글 식별자")
                ),
                responseFields(
                    fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("회원 이미지"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회 수"),
                    fieldWithPath("ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                    fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정일"),
                    fieldWithPath("answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                    fieldWithPath("tagString").type(JsonFieldType.STRING).description("태그")
                )
            ));
    }

    @Test
    public void getQuestionsTest() throws Exception {

        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Member member1 = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);

        Member member2 = new Member(2L, "hgd2@gmail.com", "12345678a", "oheadnah2", 2L);

        Question question1 = new Question(1L, "제목1", "내용1", 0 , 0, createdAt, modifiedAt);
        question1.setMember(member1);

        Question question2 = new Question(2L, "제목2", "내용2", 0 , 0, createdAt, modifiedAt);
        question2.setMember(member2);

        Page<Question> page = new PageImpl<>(List.of(question1, question2));

        long count = 2L;

        given(questionService.findQuestions(Mockito.any()))
            .willReturn(page);

        given(questionService.findQuestionCount()).willReturn(count);

        List<Question> questions = List.of(question1, question2);
        given(mapper.questionsToQuestionResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new QuestionDto.Response(
                        questions.get(0).getQuestionId(),
                        questions.get(0).getTitle(),
                        questions.get(0).getText(),
                        questions.get(0).getMember().getMemberId(),
                        questions.get(0).getMember().getMemberImage(),
                        questions.get(0).getMember().getName(),
                        questions.get(0).getViews(),
                        questions.get(0).getRatingScore(),
                        questions.get(0).getCreatedAt(),
                        questions.get(0).getModifiedAt(),
                        questions.get(0).getAnswerList().size(),
                        ""),
                    new QuestionDto.Response(
                        questions.get(1).getQuestionId(),
                        questions.get(1).getTitle(),
                        questions.get(1).getText(),
                        questions.get(1).getMember().getMemberId(),
                        questions.get(1).getMember().getMemberImage(),
                        questions.get(1).getMember().getName(),
                        questions.get(1).getViews(),
                        questions.get(1).getRatingScore(),
                        questions.get(1).getCreatedAt(),
                        questions.get(1).getModifiedAt(),
                        questions.get(1).getAnswerList().size(),
                        "")
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/questions")
                .param("page", "0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].questionId").value(questions.get(0).getQuestionId()))
            .andExpect(jsonPath("$.data[0].title").value(questions.get(0).getTitle()))
            .andExpect(jsonPath("$.data[0].text").value(questions.get(0).getText()))
            .andExpect(jsonPath("$.data[0].memberId").value(questions.get(0).getMember().getMemberId()))
            .andExpect(jsonPath("$.data[0].memberImage").value(questions.get(0).getMember().getMemberImage()))
            .andExpect(jsonPath("$.data[0].name").value(questions.get(0).getMember().getName()))
            .andExpect(jsonPath("$.data[0].views").value(questions.get(0).getViews()))
            .andExpect(jsonPath("$.data[0].ratingScore").value(questions.get(0).getRatingScore()))
            .andExpect(jsonPath("$.data[0].createdAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$.data[0].modifiedAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$.data[0].answerCount").value(questions.get(0).getAnswerList().size()))
            .andExpect(jsonPath("$.data[0].tagString").value(""))
            .andDo(document("get-questions",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                    parameterWithName("page").description("조회할 페이지 번호")
                ),
                responseFields(
                    fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("data[].text").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data[].memberImage").type(JsonFieldType.NUMBER).description("회원 이미지"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("조회 수"),
                    fieldWithPath("data[].ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                    fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                    fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정일"),
                    fieldWithPath("data[].answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                    fieldWithPath("data[].tagString").type(JsonFieldType.STRING).description("태그"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER).description("전체 회원 수")
                )
            ));

    }

    @Test
    public void deleteQuestionTest() throws Exception {

        Member member = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);
        Question question = new Question(1L, "제목1", "내용1", 0 , 0, LocalDateTime.now(), LocalDateTime.now());
        question.setMember(member);

        doNothing().when(questionService).deleteQuestion(member.getMemberId(), 1L);

        ResultActions resultActions = mockMvc.perform(
            delete("/api/questions/{question-id}", question.getQuestionId())
                .header("Authorization", "Bearer (accessToken)")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-question",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    pathParameters(parameterWithName("question-id").description("게시글 식별자")
                    ),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                )
            );
    }

    @Test
    public void getSearchQuestionTest() throws Exception {

        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Member member1 = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);

        Member member2 = new Member(2L, "hgd2@gmail.com", "12345678a", "oheadnah2", 2L);

        Question question1 = new Question(1L, "제목1", "내용1", 0 , 0, createdAt, modifiedAt);
        question1.setMember(member1);

        Question question2 = new Question(2L, "2", "2", 0 , 0, createdAt, modifiedAt);
        question2.setMember(member2);

        Question question3= new Question(3L, "제목3", "내용3", 0 , 0, createdAt, modifiedAt);
        question3.setMember(member2);



        Page<Question> page = new PageImpl<>(List.of(question1, question3));

        long count = 2L;

        given(questionService.searchQuestion(Mockito.any(), Mockito.any()))
            .willReturn(page);

        given(questionService.searchQuestionCount(Mockito.any())).willReturn(count);

        List<Question> questions = List.of(question1, question3);

        given(mapper.questionsToQuestionResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new QuestionDto.Response(
                        questions.get(0).getQuestionId(),
                        questions.get(0).getTitle(),
                        questions.get(0).getText(),
                        questions.get(0).getMember().getMemberId(),
                        questions.get(0).getMember().getMemberImage(),
                        questions.get(0).getMember().getName(),
                        questions.get(0).getViews(),
                        questions.get(0).getRatingScore(),
                        questions.get(0).getCreatedAt(),
                        questions.get(0).getModifiedAt(),
                        questions.get(0).getAnswerList().size(),
                        ""),
                    new QuestionDto.Response(
                        questions.get(1).getQuestionId(),
                        questions.get(1).getTitle(),
                        questions.get(1).getText(),
                        questions.get(1).getMember().getMemberId(),
                        questions.get(1).getMember().getMemberImage(),
                        questions.get(1).getMember().getName(),
                        questions.get(1).getViews(),
                        questions.get(1).getRatingScore(),
                        questions.get(1).getCreatedAt(),
                        questions.get(1).getModifiedAt(),
                        questions.get(1).getAnswerList().size(),
                        "")
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/questions/search")
                .param("search", "제목")
                .param("page", "0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].questionId").value(questions.get(0).getQuestionId()))
            .andExpect(jsonPath("$.data[0].title").value(questions.get(0).getTitle()))
            .andExpect(jsonPath("$.data[0].text").value(questions.get(0).getText()))
            .andExpect(jsonPath("$.data[0].memberId").value(questions.get(0).getMember().getMemberId()))
            .andExpect(jsonPath("$.data[0].memberImage").value(questions.get(0).getMember().getMemberImage()))
            .andExpect(jsonPath("$.data[0].name").value(questions.get(0).getMember().getName()))
            .andExpect(jsonPath("$.data[0].views").value(questions.get(0).getViews()))
            .andExpect(jsonPath("$.data[0].ratingScore").value(questions.get(0).getRatingScore()))
            .andExpect(jsonPath("$.data[0].createdAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$.data[0].modifiedAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$.data[0].answerCount").value(questions.get(0).getAnswerList().size()))
            .andExpect(jsonPath("$.data[0].tagString").value(""))
            .andDo(document("get-search-questions",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                    parameterWithName("search").description("검색할 게시글 제목"),
                    parameterWithName("page").description("조회할 페이지 번호")
                ),
                responseFields(
                    fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("data[].text").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data[].memberImage").type(JsonFieldType.NUMBER).description("회원 이미지"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("조회 수"),
                    fieldWithPath("data[].ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                    fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                    fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정일"),
                    fieldWithPath("data[].answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                    fieldWithPath("data[].tagString").type(JsonFieldType.STRING).description("태그"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER).description("검색된 게시글 수")
                )
            ));

    }

    @Test
    public void getMemberQuestionListTest() throws Exception {

        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Member member1 = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);


        Question question1 = new Question(1L, "제목1", "내용1", 0 , 0, createdAt, modifiedAt);
        question1.setMember(member1);

        Question question2 = new Question(2L, "2", "2", 0 , 0, createdAt, modifiedAt);
        question2.setMember(member1);

        Question question3= new Question(3L, "제목3", "내용3", 0 , 0, createdAt, modifiedAt);
        question3.setMember(member1);



        List<Question> list = List.of(question1,question2,question3);

        long count = 3L;
        long memberId = 1L;

        given(questionService.memberQuestionList(Mockito.anyLong()))
            .willReturn(list);

        List<Question> questions = List.of(question1, question2, question3);

        given(mapper.questionsToQuestionResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new QuestionDto.Response(
                        questions.get(0).getQuestionId(),
                        questions.get(0).getTitle(),
                        questions.get(0).getText(),
                        questions.get(0).getMember().getMemberId(),
                        questions.get(0).getMember().getMemberImage(),
                        questions.get(0).getMember().getName(),
                        questions.get(0).getViews(),
                        questions.get(0).getRatingScore(),
                        questions.get(0).getCreatedAt(),
                        questions.get(0).getModifiedAt(),
                        questions.get(0).getAnswerList().size(),
                        ""),
                    new QuestionDto.Response(
                        questions.get(1).getQuestionId(),
                        questions.get(1).getTitle(),
                        questions.get(1).getText(),
                        questions.get(1).getMember().getMemberId(),
                        questions.get(1).getMember().getMemberImage(),
                        questions.get(1).getMember().getName(),
                        questions.get(1).getViews(),
                        questions.get(1).getRatingScore(),
                        questions.get(1).getCreatedAt(),
                        questions.get(1).getModifiedAt(),
                        questions.get(1).getAnswerList().size(),
                        ""),
                    new QuestionDto.Response(
                        questions.get(2).getQuestionId(),
                        questions.get(2).getTitle(),
                        questions.get(2).getText(),
                        questions.get(2).getMember().getMemberId(),
                        questions.get(2).getMember().getMemberImage(),
                        questions.get(2).getMember().getName(),
                        questions.get(2).getViews(),
                        questions.get(2).getRatingScore(),
                        questions.get(2).getCreatedAt(),
                        questions.get(2).getModifiedAt(),
                        questions.get(2).getAnswerList().size(),
                        "")
                )
            );

        ResultActions resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/questions/member/{member-id}",memberId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].questionId").value(questions.get(0).getQuestionId()))
            .andExpect(jsonPath("$.data[0].title").value(questions.get(0).getTitle()))
            .andExpect(jsonPath("$.data[0].text").value(questions.get(0).getText()))
            .andExpect(jsonPath("$.data[0].memberId").value(questions.get(0).getMember().getMemberId()))
            .andExpect(jsonPath("$.data[0].memberImage").value(questions.get(0).getMember().getMemberImage()))
            .andExpect(jsonPath("$.data[0].name").value(questions.get(0).getMember().getName()))
            .andExpect(jsonPath("$.data[0].views").value(questions.get(0).getViews()))
            .andExpect(jsonPath("$.data[0].ratingScore").value(questions.get(0).getRatingScore()))
            .andExpect(jsonPath("$.data[0].createdAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$.data[0].modifiedAt").value("2022-12-31T23:59:00"))
            .andExpect(jsonPath("$.data[0].answerCount").value(questions.get(0).getAnswerList().size()))
            .andExpect(jsonPath("$.data[0].tagString").value(""))
            .andDo(document("get-member-questionList",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(parameterWithName("member-id").description("회원 식별자")
                ),
                responseFields(
                    fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("data[].text").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data[].memberImage").type(JsonFieldType.NUMBER).description("회원 이미지"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("조회 수"),
                    fieldWithPath("data[].ratingScore").type(JsonFieldType.NUMBER).description("추천 수"),
                    fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                    fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정일"),
                    fieldWithPath("data[].answerCount").type(JsonFieldType.NUMBER).description("답변 수"),
                    fieldWithPath("data[].tagString").type(JsonFieldType.STRING).description("태그"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER).description("작성한 게시글 수")
                )
            ));

    }
}
