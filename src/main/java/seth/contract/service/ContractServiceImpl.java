package seth.contract.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.dto.contract.ContractRequest;
import seth.contract.model.Contract;
import seth.contract.model.ContractType;
import seth.contract.model.Department;
import seth.contract.repository.ContractRepository;
import seth.contract.repository.ContractTypeRepository;
import seth.contract.repository.DepartmentRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final DepartmentRepository departmentRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                               ContractTypeRepository contractTypeRepository,
                               DepartmentRepository departmentRepository) {
        this.contractRepository = contractRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Contract createContract(ContractRequest contractRequest) {
        try {
            Contract contract = mapContractRequestToContract(contractRequest);
            return contractRepository.save(contract);
        } catch (IOException e) {
            throw new RuntimeException("Error creating contract: " + e.getMessage(), e);
        }
    }

    private Contract mapContractRequestToContract(ContractRequest contractRequest) throws IOException {
        Contract contract = new Contract();
        contract.setTitle(contractRequest.getTitle());

        Optional<ContractType> contractType = contractTypeRepository.findByName(contractRequest.getType());
        if (contractType.isPresent()) {
            contract.setType(contractType.get().getName()); // Adjusted to get the name directly
        } else {
            throw new IllegalArgumentException("Contract type '" + contractRequest.getType() + "' not found.");
        }

        Optional<Department> department = departmentRepository.findByName(contractRequest.getDepartment());
        if (department.isPresent()) {
            contract.setDepartment(department.get().getName()); // Adjusted to get the name directly
        } else {
            throw new IllegalArgumentException("Department '" + contractRequest.getDepartment() + "' not found.");
        }

        contract.setSigned(contractRequest.getSigned());
        contract.setContactPerson(contractRequest.getContactPerson());
        contract.setExpires(contractRequest.getExpires());
        contract.setUploadedBy(contractRequest.getUploadedBy());

        // Handle file upload
        MultipartFile file = contractRequest.getFile();
        if (file != null && !file.isEmpty()) {
            contract.setFileContent(file.getBytes());
            contract.setFileName(file.getOriginalFilename());
            contract.setFileContentType(file.getContentType());
        }

        return contract;
    }



    @Override
    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    @Transactional
    public Contract updateContract(Long id, Contract updatedContract) {

            return contractRepository.save(updatedContract);

    }

    @Override
    @Transactional
    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
}