package com.derivativemarket.posttrade.org.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import javax.swing.text.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = {SupWorkflowValidator.class})
public @interface SupWorkflowValidation {
    String message() default "Currently we are supporting these three role (IRS | NOVATION |CAPFLOOR)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
