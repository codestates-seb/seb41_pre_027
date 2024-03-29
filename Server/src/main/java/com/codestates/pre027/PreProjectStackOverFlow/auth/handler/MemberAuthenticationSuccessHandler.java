package com.codestates.pre027.PreProjectStackOverFlow.auth.handler;

import com.codestates.pre027.PreProjectStackOverFlow.auth.dto.LoginResponseDto;
import com.codestates.pre027.PreProjectStackOverFlow.member.entity.Member;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {
        // 인증 성공 후, 로그를 기록하거나 사용자 정보를 response 로 전송하는 등의 추가 작업을 할 수 있다.
        log.info("# Authenticated successfully!");

        sendSuccessResponse(response, authentication);
    }

    // 로그인시 멤버 id json 타입으로 반환
    private void sendSuccessResponse(HttpServletResponse response,
        Authentication authentication) throws IOException {
        Gson gson = new Gson();
        Member member = (Member) authentication.getPrincipal();
        LoginResponseDto dto = new LoginResponseDto();
        dto.setMemberId(member.getMemberId());
        dto.setMemberImage(member.getMemberImage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(dto, LoginResponseDto.class));
    }
}
