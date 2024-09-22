package seth.contract.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import seth.contract.dto.contract.ContractRequest;
import seth.contract.validation.constraints.ValidDateRange;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, ContractRequest.DateRange> {

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ContractRequest.DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
        final LocalDate end = getDate(dateRange.getExpires());
        if(end == null) return true;

        final LocalDate start = getDate(dateRange.getSigned());
        if(start == null) return true;

        return start.isBefore(end);
    }


    private static LocalDate getDate(String date){
        if(date == null) return null;
        try{
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
