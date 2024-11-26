package seth.contract.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Data
@Entity
@Getter @Setter
public class Contract extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate signed;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String department; // Consider using an enum or a separate Department entity

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expires;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Column(name = "uploaded_by") // Assuming you have a user entity
    private String uploadedBy; // Or use a @ManyToOne relationship to a User entity

    public Contract() {}

    // Add a constructor that takes all fields as arguments
    public Contract(String title, String type, LocalDate signed, String contactPerson, String department, LocalDate expires, String filePath, String fileName, String fileContentType, String uploadedBy) {
        this.title = title;
        this.type = type;
        this.signed = signed;
        this.contactPerson = contactPerson;
        this.department = department;
        this.expires = expires;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileContentType = fileContentType;
        this.uploadedBy = uploadedBy;
    }

}
