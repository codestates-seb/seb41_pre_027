package com.codestates.pre027.PreProjectStackOverFlow.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberCountMultiResponseDto<T> {

    private List<T> data;

    private long memberCount;

    public MemberCountMultiResponseDto(List<T> data, long memberCount) {
        this.data = data;
        this.memberCount = memberCount;
    }
}
