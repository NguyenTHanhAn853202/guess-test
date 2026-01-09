package com.app.guess.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.guess.constant.ErrorCode;
import com.app.guess.dto.response.ErrorResponse;
import com.app.guess.utils.ErrorField;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAppException(AppException ex) {
        ErrorCode errorCode = ErrorCode.getErroCodeFromCode(ex.getCode());
        ErrorResponse errorResponse = new ErrorResponse(errorCode, ex.getErrorFields());

        return errorResponse;
    }

    // lambok validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorField> errorField = ErrorField.getErrorCodes(ex);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST, errorField);
        return errorResponse;
    }

    // jwt exception
    @ExceptionHandler({ MalformedJwtException.class, ExpiredJwtException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleMalformedJwtException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED);
        return errorResponse;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_ERROR);
        return errorResponse;
    }
}
