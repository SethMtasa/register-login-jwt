package seth.contract.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import seth.contract.validation.validator.FileRequiredValidator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileRequiredValidator.class)
@Documented
public @interface FileRequired {
    String message() default "File is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
