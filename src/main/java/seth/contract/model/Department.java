package seth.contract.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Department {
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

    public static Department newWithId(long id){
        final Department department = new Department();
        department.id = id;
        return department;
    }

    public static abstract class Meta {
        public static final String ID = "id";
        public static final String NAME = "name";
    }
}
