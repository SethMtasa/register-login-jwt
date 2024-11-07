package seth.contract.service;

import seth.contract.dto.contract.ContractTypeRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.ContractType;


import java.util.List;

public interface ContractTypeService {
    ApiResponse<ContractType> addNewContractType(ContractTypeRequest contractTypeRequest) throws  Exception;
    ApiResponse<List<ContractType>> getAllContractTypes();
    ApiResponse<ContractType> updateContractType(Long id, ContractTypeRequest contractTypeRequest) ;
    ApiResponse<String> deleteContractType(Long id) ;

}
