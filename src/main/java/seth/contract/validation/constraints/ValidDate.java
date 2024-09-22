package seth.contract.validation.constraints;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import seth.contract.validation.validator.DateValidator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface ValidDate {
    String message() default "Invalid Date provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
