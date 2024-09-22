package seth.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import seth.contract.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
}
