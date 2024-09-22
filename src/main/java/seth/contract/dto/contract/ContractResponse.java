package seth.contract.dto.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContractResponse {
    private final long id;
    private final String title;
    private final String signed;
    private final String expires;
    private final String contactPerson;

    private final long deptId;
    private final String deptName;

    private final long typeId;
    private final String typeName;

    private final String createdBy;

    @JsonIgnore
    private final String fileName;
    @JsonIgnore
    private final String fileType;
    @JsonIgnore
    private final String fileDirectory;

    public ContractResponse(long id, String title, LocalDate signed, LocalDate expires, String contactPerson, long deptId, String deptName, long typeId, String typeName, String createdBy) {
        this(id, title, signed, expires, contactPerson, deptId, deptName, typeId, typeName, createdBy, null, null, null);
    }

    public ContractResponse(long id, String title, LocalDate expires, String typeName){
        this(id, title, null, expires, null, 0, null, 0, typeName, null);
    }

    public ContractResponse(String fileName, String fileType, String fileDirectory) {
        this(0, null, null, null, null, 0, null, 0, null, null, fileName, fileType, fileDirectory);
    }

    private ContractResponse(long id, String title, LocalDate signed, LocalDate expires, String contactPerson, long deptId, String deptName, long typeId, String typeName, String createdBy, String fileName, String fileType, String fileDirectory) {
        this.id = id;
        this.title = title;
        this.signed = signed == null ? null : signed.toString();
        this.expires = expires == null ? null : expires.toString();
        this.contactPerson = contactPerson;
        this.deptId = deptId;
        this.deptName = deptName;
        this.typeId = typeId;
        this.typeName = typeName;
        this.createdBy = createdBy;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileDirectory = fileDirectory;
    }
}
