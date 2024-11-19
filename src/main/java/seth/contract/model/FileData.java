package seth.contract.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table(name = "FileData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Audited(targetAuditMode = NOT_AUDITED, withModifiedFlag = true)
public class FileData extends BaseEntity {
    private String name;
    private String type;
    private String filePath;


}
