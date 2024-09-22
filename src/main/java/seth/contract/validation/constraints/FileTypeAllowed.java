package seth.contract.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import seth.contract.validation.validator.MultipartFileTypeValidator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileTypeValidator.class)
@Documented
public @interface FileTypeAllowed {
    String message() default "File Type is not allowed";

    String[] value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
