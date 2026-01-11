package com.app.guess.dto.request;

import com.app.guess.validation.constraint.PasswordMatcher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatcher(passwordField = "password", confirmPasswordField = "confirmPassword")
public class SignUpRequest {
    @NotBlank(message = "NOT_BLANK")
    @Size(min = 8, message = "LENGTH")
    private String username;

    @NotBlank(message = "NOT_BLANK")
    @Size(min = 8, message = "LENGTH")
    private String password;

    private String confirmPassword;

}
