package com.app.guess.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.app.guess.validation.validator.PasswordMatcherValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordMatcherValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatcher {
    String message() default "PASSWORD_MISMATCH";

    String passwordField();

    String confirmPasswordField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
