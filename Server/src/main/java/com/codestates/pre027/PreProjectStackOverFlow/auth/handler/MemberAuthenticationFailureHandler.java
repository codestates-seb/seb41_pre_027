package com.codestates.pre027.PreProjectStackOverFlow.auth.handler;


import com.codestates.pre027.PreProjectStackOverFlow.response.ErrorResponse;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // 인증 실패 시, 에러 로그를 기록하거나 error response 를 전송할 수 있다.
        log.error("# Authentication failed: {}", exception.getMessage());

        sendErrorResponse(response);
    }

    //  sendErrorResponse() 메서드를 호출해 출력 스트림에 Error 정보를 담고 있습니다.
    private void sendErrorResponse(HttpServletResponse response) throws IOException {

        //  Error 정보가 담긴 객체를 JSON 문자열로 변환하는데 사용되는 Gson 라이브러리의 인스턴스를 생성합니다.
        Gson gson = new Gson();

        //  ErrorResponse.of() 메서드로 HttpStatus.UNAUTHORIZED 상태 코드를 전달
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED);

        //  response 의 Content Type 이 “application/json” 이라는 것을 클라이언트에게 전달
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //  response 의 status 가 401임을 클라이언트에게 전달
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        //  Gson 을 이용해 ErrorResponse 객체를 JSON 포맷 문자열로 변환 후, 출력 스트림을 생성
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }
}
