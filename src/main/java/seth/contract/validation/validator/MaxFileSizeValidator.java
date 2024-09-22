package seth.contract.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.validation.FileSizeConstants;
import seth.contract.validation.constraints.MaxFileSize;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {
    private FileSizeConstants MAX_FILE_SIZE;

    @Override
    public void initialize(MaxFileSize constraintAnnotation) {
        this.MAX_FILE_SIZE = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || multipartFile.isEmpty() || multipartFile.getSize() <= MAX_FILE_SIZE.bytes();
    }
}
