package seth.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import seth.contract.model.ContractType;

public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    boolean existsByName(String name);
}
