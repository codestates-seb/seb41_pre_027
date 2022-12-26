package com.codestates.pre027.PreProjectStackOverFlow.controller;

import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.controller.MemberController;
import com.codestates.pre027.PreProjectStackOverFlow.member.dto.MemberDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.mapper.MemberMapper;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WithMockUser
public class MemberControllerRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private Gson gson;

    @Test
    public void postMemberTest() throws Exception {

        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com", "12345678a", "oheadnah", 1L);
        String content = gson.toJson(post);

        MemberDto.Response responseDto =
            new MemberDto.Response(1L,
                "hgd@gmail.com",
                "oheadnah",
                1L);

        given(mapper.memberPostDtoToMember(Mockito.any(MemberDto.Post.class))).willReturn(
            new Member());

        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());

        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(responseDto);

        ResultActions actions =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/member").with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(post.getEmail()))
            .andExpect(jsonPath("$.name").value(post.getName()))
            .andExpect(jsonPath("$.memberImage").value(post.getMemberImage()))
            .andDo(document(
                "post-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                    List.of(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                        fieldWithPath("memberImage").type(JsonFieldType.NUMBER)
                            .description("멤버 이미지")
                    )
                ),
                responseFields(
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("멤버 이미지")
                )
            ));
    }

    @Test
    public void patchMemberTest() throws Exception {
        // given
        long memberId = 1L;
        MemberDto.Patch patch = new MemberDto.Patch(memberId, "123456789a", "oheadnah1");
        String content = gson.toJson(patch);

        MemberDto.Response responseDto =
            new MemberDto.Response(1L,
                "hgd@gmail.com",
                "oheadnah1",
                1L);

        given(mapper.memberPatchDtoToMember(Mockito.any(MemberDto.Patch.class))).willReturn(
            new Member());

        given(memberService.updateMember(Mockito.any(Member.class), Mockito.anyLong())).willReturn(
            new Member());

        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
            mockMvc.perform(
                patch("/api/member/{member-id}", memberId).with(csrf())
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        // then
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.memberId").value(patch.getMemberId()))
            .andExpect(jsonPath("$.name").value(patch.getName()))
            .andDo(document("patch-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("member-id").description("회원 식별자")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken)")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                            .ignored(),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임")
                            .optional(),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                            .optional()
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                        fieldWithPath("memberImage").type(JsonFieldType.NUMBER)
                            .description("멤버 이미지")
                    )
                )
            ));
    }

    @Test
    public void getMemberTest() throws Exception {
        long memberId = 1L;
        MemberDto.Response response = new MemberDto.Response(1L,
            "hgd@gmail.com",
            "oheadnah",
            1L);

        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

        ResultActions resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/member/{member-id}", memberId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.memberId").value(response.getMemberId()))
            .andExpect(jsonPath("$.email").value(response.getEmail()))
            .andExpect(jsonPath("$.name").value(response.getName()))
            .andExpect(jsonPath("$.memberImage").value(response.getMemberImage()))
            .andDo(document("get-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("member-id").description("회원 식별자")
                ),
                responseFields(
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("멤버 이미지")
                )
            ));
    }

    @Test
    public void getMembersTest() throws Exception {
        Member member1 = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);

        Member member2 = new Member(2L, "hgd2@gmail.com", "12345678a", "oheadnah2", 2L);

        Page<Member> page = new PageImpl<>(List.of(member1, member2));

        long count = 2L;

        given(memberService.findMembers(Mockito.any()))
            .willReturn(page);

        given(memberService.findMemberCount()).willReturn(count);

        List<Member> members = List.of(member1, member2);
        given(mapper.membersToMemberResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new MemberDto.Response(
                        members.get(0).getMemberId(),
                        members.get(0).getEmail(),
                        members.get(0).getName(),
                        members.get(0).getMemberImage()),
                    new MemberDto.Response(
                        members.get(1).getMemberId(),
                        members.get(1).getEmail(),
                        members.get(1).getName(),
                        members.get(1).getMemberImage())
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/member")
                .param("page", "0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].memberId").value(members.get(0).getMemberId()))
            .andExpect(jsonPath("$.data[0].email").value(members.get(0).getEmail()))
            .andDo(document("get-members",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                    parameterWithName("page").description("조회할 페이지 번호")
                ),
                responseFields(
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER)
                        .description("회원 식별자"),
                    fieldWithPath("data[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data[].memberImage").type(JsonFieldType.NUMBER)
                        .description("멤버 이미지"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER).description("전체 회원 수")
                )
            ));

    }

    @Test
    public void deleteMemberTest() throws Exception {
        Member member = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);

        doNothing().when(memberService).deleteMember(member.getMemberId(), 1L);

        ResultActions resultActions = mockMvc.perform(
            delete("/api/member/{member-id}", member.getMemberId()).with(csrf())
                .header("Authorization", "Bearer (accessToken)")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-member",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    pathParameters(parameterWithName("member-id").description("회원 식별자")
                    ),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                )
            );
    }

    @Test
    public void getSearchMemberTest() throws Exception {
        Member member1 = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);

        Member member2 = new Member(2L, "hgd2@gmail.com", "12345678a", "eadnah", 2L);

        Member member3 = new Member(3L, "hgd3@gmail.com", "12345678a", "oheadnah", 3L);

        Page<Member> page = new PageImpl<>(List.of(member1, member3));

        long count = 2L;

        given(memberService.searchMember(Mockito.any(), Mockito.any()))
            .willReturn(page);

        given(memberService.searchMemberCount(Mockito.any())).willReturn(count);

        List<Member> members = List.of(member1, member3);
        given(mapper.membersToMemberResponseDtos(Mockito.anyList()))
            .willReturn(
                List.of(
                    new MemberDto.Response(
                        members.get(0).getMemberId(),
                        members.get(0).getEmail(),
                        members.get(0).getName(),
                        members.get(0).getMemberImage()),
                    new MemberDto.Response(
                        members.get(1).getMemberId(),
                        members.get(1).getEmail(),
                        members.get(1).getName(),
                        members.get(1).getMemberImage())
                )
            );

        ResultActions resultActions = mockMvc.perform(
            get("/api/member/search")
                .param("search", "oh")
                .param("page", "0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].memberId").value(members.get(0).getMemberId()))
            .andExpect(jsonPath("$.data[0].email").value(members.get(0).getEmail()))
            .andDo(document("get-search-members",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                    parameterWithName("search").description("검색할 단어"),
                    parameterWithName("page").description("조회할 페이지 번호")
                ),
                responseFields(
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER)
                        .description("회원 식별자"),
                    fieldWithPath("data[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data[].memberImage").type(JsonFieldType.NUMBER)
                        .description("멤버 이미지"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER).description("검색된 회원 수")
                )
            ));

    }
}