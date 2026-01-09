package com.app.guess.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.app.guess.constant.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorField {
    private String errorCode;
    private String errorMessage;
    private String fieldName;

    public ErrorField(ErrorCode errorCode, String fieldName) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
        this.fieldName = fieldName;
    }

    public static List<ErrorField> getErrorCodes(MethodArgumentNotValidException ex) {
        List<ErrorField> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> {
            ErrorCode errorCode = ErrorCode.valueOf(error.getDefaultMessage());
            String errorField = error.getField();
            return new ErrorField(errorCode, errorField);
        }).collect(Collectors.toList());

        return errors;
    }
}
