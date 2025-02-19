package seth.contract.service;

import seth.contract.dto.contract.ContractRequest;
import seth.contract.exception.UnsupportedFileTypeException;
import seth.contract.model.Contract;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ContractService {

    Contract createContract(ContractRequest contractRequest);

    Optional<Contract> getContractById(Long id);

    List<Contract> getAllContracts();
    byte[] getContractFileById(Long id) throws IOException, UnsupportedFileTypeException;

    Contract updateContract(Long id, Contract updatedContract);

    void deleteContract(Long id);
}