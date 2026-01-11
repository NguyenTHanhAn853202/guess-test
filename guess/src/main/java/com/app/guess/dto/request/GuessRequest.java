package com.app.guess.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuessRequest {
    @NotNull(message = "NOT_BLANK")
    @Min(value = 1, message = "RANGE_VALUE")
    @Max(value = 5, message = "RANGE_VALUE")
    private int number;
}
