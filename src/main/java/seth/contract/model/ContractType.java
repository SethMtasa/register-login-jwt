package seth.contract.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class ContractType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "active_status")
    private boolean activeStatus;

    public void setActiveStatus(boolean activeStatus) { this.activeStatus = activeStatus; }

    @PrePersist
    public void prePersist(){ this.activeStatus = true; }

    public static ContractType newWithId(long id){
        final ContractType type = new ContractType();
        type.id = id;
        return type;
    }

    public static abstract class Meta {
        public static final String ID = "id";
        public static final String NAME = "name";
    }
}