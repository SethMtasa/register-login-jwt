package seth.contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import seth.contract.dto.contract.ContractTypeRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.ContractType;
import seth.contract.model.Department;
import seth.contract.repository.ContractRepository;
import seth.contract.repository.ContractTypeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ContractTypeServiceImpl implements ContractTypeService {

    private final ContractTypeRepository contractTypeRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public ContractTypeServiceImpl(ContractTypeRepository contractTypeRepository,
                                   ContractRepository contractRepository) {
        this.contractTypeRepository = contractTypeRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public ApiResponse<ContractType> addNewContractType(ContractTypeRequest contractTypeRequest) throws Exception {
        // Changed return type
            try {
                ContractType contractType = new ContractType();
                contractType.setName(contractTypeRequest.name()); // Assuming name is relevant for contract types
                contractType.setActiveStatus(true); // Assuming activeStatus is relevant
                ContractType savedContractType = contractTypeRepository.save(contractType);
                return new ApiResponse<>(true, "Contract type added successfully", savedContractType);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Contract type with this name already exists.", e);
            } catch (Exception e) {
                throw new Exception("Failed to add contract type: " + e.getMessage(), e);
            }
        }


    @Override
    public ApiResponse<List<ContractType>> getAllContractTypes() {
        try {
            List<ContractType> contractTypes = contractTypeRepository.findAll();
            return new ApiResponse<>(true, "Contract types retrieved successfully", contractTypes);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Failed to retrieve contract types: " + e.getMessage(), List.of());
        }
    }

    @Override
    public ApiResponse<ContractType> updateContractType(Long id, ContractTypeRequest contractTypeRequest) {
        {
            try {
                Optional<ContractType> optionalContractType = contractTypeRepository.findById(id);

                if (optionalContractType.isPresent()) {
                    ContractType contractType = optionalContractType.get();
                    contractType.setName(contractTypeRequest.name());
                    ContractType updatedDepartment = contractTypeRepository.save(contractType);
                    return new ApiResponse<>(true, "ContractType updated successfully", updatedDepartment);
                } else {
                    return new ApiResponse<>(false, "ContractType not found", null);
                }
            } catch (Exception e) {
                return new ApiResponse<>(false, "Failed to update ContractType: " + e.getMessage(), null);
            }
        }
    }

    @Override
    public ApiResponse<String> deleteContractType(Long id) {
        try {
            Optional<ContractType> optionalContractType = contractTypeRepository.findById(id);
            if (optionalContractType.isPresent()) {
                contractTypeRepository.deleteById(id);
                return new ApiResponse<>(true, "ContractType deleted successfully", null);
            } else {
                return new ApiResponse<>(false, "ContractType not found", null);
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, "Failed to delete ContractType: " + e.getMessage(), null);
        }
    }
    }

