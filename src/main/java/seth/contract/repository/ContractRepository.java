package seth.contract.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seth.contract.dto.contract.ContractResponse;
import seth.contract.model.Contract;


import java.time.LocalDate;
import java.util.List;


public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "SELECT c.id, c.title, c.expires, ct.name FROM Contract c JOIN Contract_Type ct ON c.type_id = ct.id WHERE :now BETWEEN (c.expires - INTERVAL :months MONTH) AND c.expires", nativeQuery = true)
    List<ContractResponse> findExpiringSoon(LocalDate now, int months);
}
