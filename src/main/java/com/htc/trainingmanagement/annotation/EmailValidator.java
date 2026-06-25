package com.htc.trainingmanagement.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String TRAINEE_DOMAIN = "@htcinc.com";
    private static final String ADMIN_TRAINER_DOMAIN = "@HTCinc.com";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub

        return value.endsWith(TRAINEE_DOMAIN) || value.endsWith(ADMIN_TRAINER_DOMAIN);

    }

}
