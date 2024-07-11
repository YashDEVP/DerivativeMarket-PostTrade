package com.derivativemarket.posttrade.org.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SupWorkflowValidator implements ConstraintValidator<SupWorkflowValidation,String>{
    @Override
    public boolean isValid(String inputWorkflow, ConstraintValidatorContext constraintValidatorContext) {
        List<String> workflows=List.of("SEF","CAPFLOOR","NOVATION");
        return workflows.contains(inputWorkflow);
    }
}
