package com.codestates.pre027.PreProjectStackOverFlow.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CountMultiResponseDto<T> {

    private List<T> data;

    private long count;

    public CountMultiResponseDto(List<T> data, long count) {
        this.data = data;
        this.count = count;
    }
}
