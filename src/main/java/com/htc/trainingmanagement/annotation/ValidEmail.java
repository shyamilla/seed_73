package com.htc.trainingmanagement.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Can be applied only on a class (DTO)
@Target(ElementType.TYPE)

// Validation annotation is available at runtime
@Retention(RetentionPolicy.RUNTIME)

// Specifies the validator class that contains the validation logic
@Constraint(validatedBy = EmailValidator.class)

public @interface ValidEmail {

    // Default validation error message
    String message() default "Email must end with a proper domain name (@htcinc.com or @HTCinc.com)";

    // Used to group validation constraints (rarely used)
    Class<?>[] groups() default {};

    // Used to attach custom metadata to validation errors
    Class<? extends Payload>[] payload() default {};

}