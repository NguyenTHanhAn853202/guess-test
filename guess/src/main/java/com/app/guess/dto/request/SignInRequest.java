package com.app.guess.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @NotBlank(message = "NOT_BLANK")
    @Size(min = 8, message = "LENGTH")
    private String username;

    @NotBlank(message = "NOT_BLANK")
    @Size(min = 8, message = "LENGTH")
    private String password;
}
