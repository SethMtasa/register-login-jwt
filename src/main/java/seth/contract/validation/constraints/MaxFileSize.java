package seth.contract.validation.constraints;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import seth.contract.validation.FileSizeConstants;
import seth.contract.validation.validator.MaxFileSizeValidator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Documented
public @interface MaxFileSize {
    String message() default "File exceeds maximum size";

    FileSizeConstants value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
