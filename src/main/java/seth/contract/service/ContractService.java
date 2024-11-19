package seth.contract.service;

import seth.contract.dto.contract.ContractRequest;
import seth.contract.model.Contract;
import java.util.List;
import java.util.Optional;

public interface ContractService {

    Contract createContract(ContractRequest contractRequest);

    Optional<Contract> getContractById(Long id);

    List<Contract> getAllContracts();

    Contract updateContract(Long id, Contract updatedContract);

    void deleteContract(Long id);
}