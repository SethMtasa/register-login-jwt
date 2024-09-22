package seth.contract.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.validation.FileSizeConstants;
import seth.contract.validation.constraints.*;


@Getter @Setter
public class ContractRequest {
    public ContractRequest() { this.dateRange = new DateRange(); }

    @NotBlank(message = "Title is required")
    private String title;

    @JsonIgnore
    @Valid
    @ValidDateRange(message = "Expiry date must come after Signed Date")
    private DateRange dateRange;

    @Min(value = 1L, message = "Contract Type is required")
    private long typeId;

    @Min(value = 1L, message = "Department is required")
    private long deptId;

    @FileRequired
    @FileTypeAllowed(value = MediaType.APPLICATION_PDF_VALUE, message = "Only PDF files allowed")
    @MaxFileSize(value = FileSizeConstants.FIVE_MEGABYTES, message = "File is too large. Max: 5MB")
    private MultipartFile file;

    @NotBlank(message = "Contact Person is required")
    private String contactPerson;

    /********** Inner Class **********/
    @Getter
    public static class DateRange {
        @NotBlank(message = "Signed Date is required")
        @ValidDate(message = "Please provide a valid Signed Date")
        private String signed;

        @ValidDate(message = "Please provide a valid Expires Date")
        private String expires;
    }

    /************* Setters And Getters *************/
    public void setSigned(String signed) { this.dateRange.signed = signed; }

    public void setExpires(String expires) { this.dateRange.expires = expires; }
    public String getSigned() { return dateRange.signed; }
    public String getExpires() { return dateRange.expires; }
}