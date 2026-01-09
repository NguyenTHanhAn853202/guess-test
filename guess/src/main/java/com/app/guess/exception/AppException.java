package com.app.guess.exception;

import java.util.List;

import com.app.guess.constant.ErrorCode;
import com.app.guess.utils.ErrorField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    private String message;
    private String code;
    private List<ErrorField> errorFields;

    public AppException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public AppException(ErrorCode errorCode, ErrorField errorField) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.errorFields = List.of(errorField);
    }

    public AppException(ErrorCode errorCode, List<ErrorField> errorFields) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.errorFields = errorFields;
    }
}
