package com.htc.trainingmanagement.annotation;

import com.htc.trainingmanagement.enums.RoleName;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String TRAINEE_DOMAIN = "@htcinc.com";
    private static final String ADMIN_TRAINER_DOMAIN = "@HTCinc.com";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub

        String email = value.getEmail();

        if (value.getRoles().contains(RoleName.ADMIN)
                || value.getRoles().contains(RoleName.TRAINER)) {

            return email.endsWith(ADMIN_TRAINER_DOMAIN);
        }

        if (value.getRoles().contains(RoleName.TRAINEE)) {

            return email.endsWith(TRAINEE_DOMAIN);
        }

        return false;

        // return value.endsWith(TRAINEE_DOMAIN) ||
        // value.endsWith(ADMIN_TRAINER_DOMAIN);

    }

}
