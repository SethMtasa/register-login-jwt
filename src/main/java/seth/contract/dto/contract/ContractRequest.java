package seth.contract.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;


@Data
public class ContractRequest {

    @JsonIgnore
    @Valid
    private String title;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate signed;
    private String contactPerson;
    private String department;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expires;
    private MultipartFile file;
    private String uploadedBy;


}