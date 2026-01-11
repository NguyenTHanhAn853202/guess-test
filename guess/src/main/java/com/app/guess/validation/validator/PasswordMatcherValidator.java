package com.app.guess.validation.validator;

import org.springframework.beans.BeanWrapperImpl;

import com.app.guess.validation.constraint.PasswordMatcher;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        var password = new BeanWrapperImpl(value).getPropertyValue(passwordField);
        var configPassword = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);
        var isValid = password.equals(configPassword);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("PASSWORD_MISMATCH")
                    .addPropertyNode(confirmPasswordField)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
