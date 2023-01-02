package com.codestates.pre027.PreProjectStackOverFlow.auth.utils;


import com.codestates.pre027.PreProjectStackOverFlow.response.ErrorResponse;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

//  ErrorResponse 를 클라이언트에게 전송하기 위한 ErrorResponder 클래스
public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }
}
