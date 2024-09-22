//package seth.contract.validation.validator;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.util.StringUtils;
//import seth.contract.validation.constraints.IsSystemRole;
//
//
//public class SystemRoleValidator implements ConstraintValidator<IsSystemRole, String> {
//    private static final SystemRole[] SYSTEM_ROLES = SystemRole.values();
//
//    @Override
//    public void initialize(IsSystemRole constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
//        if(! StringUtils.hasText(role)) return true;
//
//        final String r = role.toUpperCase();
//
//        for(SystemRole systemRole: SYSTEM_ROLES) if(systemRole.name().equals(r)) return true;
//
//        return false;
//    }
//}
