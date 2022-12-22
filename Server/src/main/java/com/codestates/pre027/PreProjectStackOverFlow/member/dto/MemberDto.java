package com.codestates.pre027.PreProjectStackOverFlow.member.dto;

import com.codestates.pre027.PreProjectStackOverFlow.validator.NotSpace;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class MemberDto {

    @Getter
    @AllArgsConstructor
    //    controller 에서 postMember 로 받아오는 값
    public static class Post {

        @Email
        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 최소 8자이상 문자와 숫자가 들어가야 합니다")
        private String password;

        @NotBlank(message = "회원 닉네임은 공백이 아니어야 합니다.")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    //    controller 에서 patchMember 로 받아오는 값
    public static class Patch {

        @Setter
        private long memberId;

        private String email;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "비밀번호는 최소 8자이상 문자와 숫자가 들어가야 합니다")
        private String password;

        @NotSpace(message = "회원 닉네임은 공백이 아니어야 합니다")
        private String name;

    }

    @Getter
    @AllArgsConstructor
    //    controller 에서 Response 로 보내주는 값
    public static class Response {

        private long memberId;

        private String email;

        private String name;

    }
}
