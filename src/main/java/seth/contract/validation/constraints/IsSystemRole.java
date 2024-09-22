//package seth.contract.validation.constraints;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//import seth.contract.validation.validator.SystemRoleValidator;
//
//import java.lang.annotation.*;
//
//@Target({ElementType.METHOD, ElementType.FIELD})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = SystemRoleValidator.class)
//@Documented
//public @interface IsSystemRole {
//    String message() default "Invalid Role provided";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//}
