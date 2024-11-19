package seth.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seth.contract.model.ContractType;

import java.util.Optional;

public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    boolean existsByName(String name);

    @Query("SELECT ct FROM ContractType ct WHERE ct.name = :name")
    Optional<ContractType> findByName(@Param("name") String name);

}
