package com.app.guess.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.app.guess.constant.ErrorCode;
import com.app.guess.utils.ErrorField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String code;

    private String message;

    private List<ErrorField> errors = new ArrayList<>();

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(ErrorCode errorCode, ErrorField errors) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = List.of(errors);
    }

    public ErrorResponse(ErrorCode errorCode, List<ErrorField> errors) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }
}
