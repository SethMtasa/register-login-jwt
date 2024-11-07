package seth.contract.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;



@Getter @Setter
public class ContractRequest {
    public ContractRequest() { this.dateRange = new DateRange(); }

    @NotBlank(message = "Title is required")
    private String title;

    @JsonIgnore
    @Valid

    private DateRange dateRange;

    @Min(value = 1L, message = "Contract Type is required")
    private long typeId;

    @Min(value = 1L, message = "Department is required")
    private long deptId;


    private MultipartFile file;

    @NotBlank(message = "Contact Person is required")
    private String contactPerson;

    /********** Inner Class **********/
    @Getter
    public static class DateRange {
        @NotBlank(message = "Signed Date is required")

        private String signed;


        private String expires;
    }

    /************* Setters And Getters *************/
    public void setSigned(String signed) { this.dateRange.signed = signed; }

    public void setExpires(String expires) { this.dateRange.expires = expires; }
    public String getSigned() { return dateRange.signed; }
    public String getExpires() { return dateRange.expires; }
}