package seth.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seth.contract.model.ContractType;
import seth.contract.model.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);

    @Query("SELECT ct FROM Department ct WHERE ct.name = :name")
    Optional<Department> findByName(@Param("name") String name);
}
