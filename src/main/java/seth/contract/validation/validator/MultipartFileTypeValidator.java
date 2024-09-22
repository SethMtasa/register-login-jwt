package seth.contract.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.validation.constraints.FileTypeAllowed;

public class MultipartFileTypeValidator implements ConstraintValidator<FileTypeAllowed, MultipartFile> {
    private String[] ALLOWED_MEDIA_TYPES;

    @Override
    public void initialize(FileTypeAllowed constraintAnnotation) {
        this.ALLOWED_MEDIA_TYPES = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile == null || multipartFile.isEmpty()) return true;

        for(String type: ALLOWED_MEDIA_TYPES)
            if(type.equals(multipartFile.getContentType())) return true;

        return false;
    }
}
