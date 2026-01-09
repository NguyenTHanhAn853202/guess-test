package com.app.guess.dto.response;

import com.app.guess.constant.ErrorCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String code;

    private String message;

    private T data;

    public ApiResponse(T data) {
        this.data = data;
        this.message = ErrorCode.SUCCESS.getMessage();
        this.code = ErrorCode.SUCCESS.getCode();
    }
}
