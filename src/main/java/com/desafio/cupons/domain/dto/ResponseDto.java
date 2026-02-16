package com.desafio.cupons.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private final String message;
    private final T data;

    public ResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
