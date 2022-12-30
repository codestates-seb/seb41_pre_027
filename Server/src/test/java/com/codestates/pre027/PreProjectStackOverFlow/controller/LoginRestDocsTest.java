package com.codestates.pre027.PreProjectStackOverFlow.controller;

import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.pre027.PreProjectStackOverFlow.controller.ApiDocumentUtils.getResponsePreProcessor;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codestates.pre027.PreProjectStackOverFlow.auth.dto.LoginDto;
import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.codestates.pre027.PreProjectStackOverFlow.member.service.MemberService;
import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "ec2-15-164-211-228.ap-northeast-2.compute.amazonaws.com")
public class LoginRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private MemberService memberService;

    @Test
    public void LoginTest() throws Exception {

        Member member = new Member(1L, "hgd@gmail.com", "12345678a", "oheadnah", 1L);
        memberService.createMember(member);

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(member.getEmail());
        loginDto.setPassword("12345678a");
        String content = gson.toJson(loginDto);

        ResultActions actions = mockMvc.perform(
            post("/api/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.memberId").value(member.getMemberId()))
            .andExpect(jsonPath("$.memberImage").value(member.getMemberImage()))
            .andDo(document(
                "login-account",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                    List.of(
                        fieldWithPath("username").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    )
                ),
                responseHeaders(
                    List.of(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("memberImage").type(JsonFieldType.NUMBER).description("회원 이미지")
                    )
                )
            ));
    }
}
