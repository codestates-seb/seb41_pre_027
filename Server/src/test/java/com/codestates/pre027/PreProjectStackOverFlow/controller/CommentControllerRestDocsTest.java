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

import com.codestates.pre027.PreProjectStackOverFlow.answer.entity.Answer;
import com.codestates.pre027.PreProjectStackOverFlow.answer.service.AnswerService;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.comment.controller.CommentController;
import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto;
import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto.Patch;
import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto.Post;
import com.codestates.pre027.PreProjectStackOverFlow.comment.dto.CommentDto.Response;
import com.codestates.pre027.PreProjectStackOverFlow.comment.entity.Comment;
import com.codestates.pre027.PreProjectStackOverFlow.comment.mapper.CommentMapper;
import com.codestates.pre027.PreProjectStackOverFlow.comment.service.CommentService;
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

@WebMvcTest(value = CommentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "ec2-15-164-211-228.ap-northeast-2.compute.amazonaws.com")
public class CommentControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private MemberService memberService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerService answerService;

    @Autowired
    private Gson gson;

    @Test
    public void postCommentToQuestionTest() throws Exception{
        long questionId = 1L;

        CommentDto.Post post = new Post("질문에 달리는 댓글 내용입니다");

        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();

        CommentDto.Response response =
            new Response(1L,
                1L,
                1L,
                "DryLeonhard",
                "질문에 달리는 댓글 내용입니다",
                createdAt);

        given(commentMapper.commentPostDto_to_Comment(Mockito.any(CommentDto.Post.class)))
            .willReturn(new Comment());
        given(commentService.createCommentToQuestion(Mockito.any(Comment.class),
            Mockito.anyLong(),Mockito.anyLong())).willReturn(new Comment());
        given(commentMapper.comment_to_CommentResponseDto(Mockito.any(Comment.class)))
            .willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                post("/api/questions/{quest-id}/comments",questionId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.text").value(post.getText()))
            .andDo(document(
                "post-comment-to-question",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken")
                ),
                pathParameters(
                    parameterWithName("quest-id").description("질문 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("text").type(JsonFieldType.STRING).description("댓글 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("글 제목(질문)"),
                    fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberNick").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("답변 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                )
            ));
    }

    @Test
    public void postCommentToAnswerTest() throws Exception{
        long answerId = 1L;

        CommentDto.Post post = new Post("답변에 달리는 댓글 내용입니다");

        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();

        CommentDto.Response response =
            new Response(1L,
                1L,
                1L,
                "DryLeonhard",
                "답변에 달리는 댓글 내용입니다",
                createdAt);

        given(commentMapper.commentPostDto_to_Comment(Mockito.any(CommentDto.Post.class)))
            .willReturn(new Comment());
        given(commentService.createCommentToAnswer(Mockito.any(Comment.class),
            Mockito.anyLong(),Mockito.anyLong())).willReturn(new Comment());
        given(commentMapper.comment_to_CommentResponseDto(Mockito.any(Comment.class)))
            .willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                post("/api/answers/{answer-id}/comments",answerId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.text").value(post.getText()))
            .andDo(document(
                "post-comment-to-answers",
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
                        fieldWithPath("text").type(JsonFieldType.STRING).description("댓글 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("글 제목(답변)"),
                    fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberNick").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("답변 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                )
            ));
    }

    @Test
    public void patchCommentTest() throws Exception{
        long commentId = 1L;
        CommentDto.Patch patch = new Patch(commentId,"수정된 댓글내용입니다");
        String content = gson.toJson(patch);

        LocalDateTime createdAt = LocalDateTime.now();

        CommentDto.Response response =
            new Response(1L,
                1L,
                1L,
                "DryLeonhard",
                "수정된 댓글내용입니다",
                createdAt);

        given(commentMapper.commentPatchDto_to_Comment(Mockito.any(CommentDto.Patch.class)))
            .willReturn(new Comment());
        given(commentService.updateComment(Mockito.any(Comment.class),Mockito.anyLong()))
            .willReturn(new Comment());
        given(commentMapper.comment_to_CommentResponseDto(Mockito.any(Comment.class)))
            .willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                patch("/api/comments/{comment-id}",commentId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.text").value(patch.getText()))
            .andDo(document("patch-comment",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken")
                ),
                pathParameters(
                    parameterWithName("comment-id").description("댓글 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("수정될 댓글 식별자"),
                        fieldWithPath("text").type(JsonFieldType.STRING).description("수정된 댓글 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("상위글 식별자(질문or답변)"),
                    fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("memberNick").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("text").type(JsonFieldType.STRING).description("수정된 답변 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자")
                )
            ));
    }

    @Test
    public void getCommentFromAnswerTest() throws Exception{
        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Question question = new Question(1L,"질문 제목","질문 내용",0,0,createdAt,modifiedAt);


        Member member = new Member(1L,"dry@gmail.com","qwer1234","뜨륵이",1L);


        Answer answer = new Answer(1L,"답변 내용",createdAt,modifiedAt,member,question,null,0);

        Comment comment1 = new Comment(1L,"댓글내용 1입니다",createdAt,false,member,null,answer);
        Comment comment2 = new Comment(2L,"댓글내용 2입니다",createdAt,false,member,null,answer);
        Comment comment3 = new Comment(2L,"댓글내용 3입니다",createdAt,true,member,null,null);

        List<Comment> comments = List.of(comment1,comment2,comment3);

        given(commentService.findCommentByQuestionId(Mockito.anyLong())).willReturn(comments);

        given(commentMapper.comments_to_CommentResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new CommentDto.Response(
                        comments.get(0).getAnswer().getAnswerId(),
                        comments.get(0).getCommentId(),
                        comments.get(0).getWriter().getMemberId(),
                        comments.get(0).getWriter().getName(),
                        comments.get(0).getText(),
                        comments.get(0).getCreatedAt()
                    ),
                    new CommentDto.Response(
                        comments.get(1).getAnswer().getAnswerId(),
                        comments.get(1).getCommentId(),
                        comments.get(1).getWriter().getMemberId(),
                        comments.get(1).getWriter().getName(),
                        comments.get(1).getText(),
                        comments.get(1).getCreatedAt()
                    )
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/answers/{answer-id}/comments",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$[0].parentId").value(comments.get(0).getAnswer().getAnswerId()))
            .andExpect(jsonPath("$[0].commentId").value(comments.get(0).getCommentId()))
            .andExpect(jsonPath("$[0].memberId").value(comments.get(0).getWriter().getMemberId()))
            .andExpect(jsonPath("$[0].memberNick").value(comments.get(0).getWriter().getName()))
            .andExpect(jsonPath("$[0].text").value(comments.get(0).getText()))
            .andExpect(jsonPath("$[0].createdAt").value("2022-12-31T23:59:00"))
            .andDo(document("get-comments-by-answer",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(parameterWithName("answer-id").description("조회할 답변 식별자")
                ),
                responseFields(
                    fieldWithPath("[].parentId").type(JsonFieldType.NUMBER).description("상위글(답변) 식별자"),
                    fieldWithPath("[].commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                    fieldWithPath("[].memberNick").type(JsonFieldType.STRING).description("작성자 닉네임"),
                    fieldWithPath("[].text").type(JsonFieldType.STRING).description("댓글 내용"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("댓글 작성일자")

                )
            ));

    }

    @Test
    public void getCommentFromQuestionTest() throws Exception{
        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Question question = new Question(1L,"질문 제목","질문 내용",0,0,createdAt,modifiedAt);

        Member member = new Member(1L,"dry@gmail.com","qwer1234","뜨륵이",1L);

        Comment comment1 = new Comment(1L,"댓글내용 1입니다",createdAt,true,member,question,null);
        Comment comment2 = new Comment(2L,"댓글내용 2입니다",createdAt,true,member,question,null);
        Comment comment3 = new Comment(2L,"댓글내용 3입니다",createdAt,false,member,null,null);

        List<Comment> comments = List.of(comment1,comment2,comment3);

        given(commentService.findCommentByQuestionId(Mockito.anyLong())).willReturn(comments);

        given(commentMapper.comments_to_CommentResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new CommentDto.Response(
                        comments.get(0).getQuest().getQuestionId(),
                        comments.get(0).getCommentId(),
                        comments.get(0).getWriter().getMemberId(),
                        comments.get(0).getWriter().getName(),
                        comments.get(0).getText(),
                        comments.get(0).getCreatedAt()
                    ),
                    new CommentDto.Response(
                        comments.get(1).getQuest().getQuestionId(),
                        comments.get(1).getCommentId(),
                        comments.get(1).getWriter().getMemberId(),
                        comments.get(1).getWriter().getName(),
                        comments.get(1).getText(),
                        comments.get(1).getCreatedAt()
                    )
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/questions/{quest-id}/comments",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$[0].parentId").value(comments.get(0).getQuest().getQuestionId()))
            .andExpect(jsonPath("$[0].commentId").value(comments.get(0).getCommentId()))
            .andExpect(jsonPath("$[0].memberId").value(comments.get(0).getWriter().getMemberId()))
            .andExpect(jsonPath("$[0].memberNick").value(comments.get(0).getWriter().getName()))
            .andExpect(jsonPath("$[0].text").value(comments.get(0).getText()))
            .andExpect(jsonPath("$[0].createdAt").value("2022-12-31T23:59:00"))
            .andDo(document("get-comments-by-question",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(parameterWithName("quest-id").description("조회할 질문 식별자")
                ),
                responseFields(
                    fieldWithPath("[].parentId").type(JsonFieldType.NUMBER).description("상위글(질문) 식별자"),
                    fieldWithPath("[].commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
                    fieldWithPath("[].memberNick").type(JsonFieldType.STRING).description("작성자 닉네임"),
                    fieldWithPath("[].text").type(JsonFieldType.STRING).description("댓글 내용"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("댓글 작성일자")

                )
            ));

    }

    @Test
    public void deleteCommentTest() throws Exception{
        LocalDateTime createdAt = LocalDateTime.of(2022,12,31,23,59,00);
        LocalDateTime modifiedAt = LocalDateTime.of(2022,12,31,23,59,00);

        Question question = new Question(1L,"질문 제목","질문 내용",0,0,createdAt,modifiedAt);

        Member member = new Member(1L,"dry@gmail.com","qwer1234","뜨륵이",1L);

        Comment comment = new Comment(1L,"댓글내용입니다",createdAt,true,member,question,null);

        comment.setWriter(member);
        comment.setQuest(question);
        comment.setAnswer(null);

        doNothing().when(commentService).deleteComment(comment.getCommentId(),1L);

        ResultActions resultActions = mockMvc.perform(
            delete("/api/comments/{comment-id}",comment.getCommentId())
                .header("Authorization", "Bearer (accessToken)")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-comment",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    pathParameters(parameterWithName("comment-id").description("댓글 식별자")
                    ),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                )
            );
    }
}
