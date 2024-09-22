package seth.contract.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Contract extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate signed;

    private LocalDate expires;

    private String contactPerson;

    private String fileName;

    private String fileType;

    private String fileDirectory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ContractType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    public void setFile(String fileName, String fileType, String fileDirectory){
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileDirectory = fileDirectory;
    }

    public static abstract class Meta {
        public static final String ID = "id";
        public static final String ACTIVE_STATUS = "activeStatus";
        public static final String TITLE = "title";
        public static final String SIGNED = "signed";
        public static final String EXPIRES = "expires";
        public static final String CONTACT_PERSON = "contactPerson";
        public static final String FILE_NAME = "fileName";
        public static final String FILE_TYPE = "fileType";
        public static final String FILE_DIRECTORY = "fileDirectory";
        public static final String CONTRACT_TYPE = "type";
        public static final String DEPARTMENT = "department";
        public static final String CREATED_BY = "createdBy";
    }
}
