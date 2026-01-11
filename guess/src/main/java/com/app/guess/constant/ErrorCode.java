package com.app.guess.constant;

public enum ErrorCode {

    // Validation errors
    NOT_BLANK("CN001", "Field must not be blank"),
    LENGTH("CN002", "Field length is invalid"),
    PASSWORD_MISMATCH("CN003", "Passwords do not match"),
    RANGE_VALUE("CN004", "Value is out of range"),

    // Business errors
    USERNAME_EXISTS("BN001", "Username already exists"),
    PASSWORD_OR_USERNAME_INVALID("BN002", "Invalid username or password"),
    TURNS_LESS_THAN_ONE("BN003", "No remaining turns"),

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
