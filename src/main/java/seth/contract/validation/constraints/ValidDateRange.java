package seth.contract.validation.constraints;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import seth.contract.validation.validator.DateRangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "Invalid Date Range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
