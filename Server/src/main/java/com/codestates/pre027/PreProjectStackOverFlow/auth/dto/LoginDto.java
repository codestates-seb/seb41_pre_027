package com.codestates.pre027.PreProjectStackOverFlow.auth.dto;

import lombok.Getter;
import lombok.Setter;

//클라이언트가 전송한 Username/Password 정보를 SecurityFilter 에서
//사용할 수 있도록 역직렬화(Deserialization)하기 위한 DTO 클래스입니다.
@Getter
@Setter
public class LoginDto {

    private String username;
    private String password;
}
