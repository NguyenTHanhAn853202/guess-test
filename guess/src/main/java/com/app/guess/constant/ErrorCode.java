package com.app.guess.constant;

public enum ErrorCode {

    // Normal Status
    SUCCESS("NM001", "The request is successful"),
    BAD_REQUEST("NM002", "Bad request"),
    UNAUTHORIZED("NM003", "Unauthorized"),
    NOT_FOUND("NM005", "Resource not found"),
    INTERNAL_ERROR("NM006", "Internal server error"),
    ;

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getErroCodeFromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code.equals(code))
                return errorCode;
        }
        throw new IllegalArgumentException("Unknow error code: " + code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
