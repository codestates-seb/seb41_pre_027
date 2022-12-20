package com.codestates.pre027.PreProjectStackOverFlow.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class MemberDto {

    @Getter
    @AllArgsConstructor
    //    controller 에서 postMember 로 받아오는 값
    public static class Post {

        private String email;

        private String password;

        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    //    controller 에서 patchMember 로 받아오는 값
    public static class Patch {

        private long memberId;

        private String email;

        private String password;

        private String name;

    }

    @Getter
    @AllArgsConstructor
    //    controller 에서 Response 로 보내주는 값
    public static class Response {

        private long memberId;

        private String email;

        private String password;

        private String name;

    }
}
