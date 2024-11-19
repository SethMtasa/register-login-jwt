package seth.contract.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seth.contract.dto.contract.ContractTypeRequest;
import seth.contract.dto.contract.DepartmentRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.ContractType;
import seth.contract.model.Department;
import seth.contract.service.ContractTypeService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contractTypes")
public class ContractTypeController {

        private final ContractTypeService contractTypeService;

        @Autowired
        public ContractTypeController(ContractTypeService contractTypeService) {
            this.contractTypeService = contractTypeService;
        }

        @PostMapping("/add")
        public ResponseEntity<ApiResponse<ContractType>> addNewContractType(@RequestBody ContractTypeRequest contractTypeRequest) {
            try {
                ApiResponse<ContractType> apiResponse = contractTypeService.addNewContractType(contractTypeRequest);
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                ApiResponse<ContractType> apiResponse = new ApiResponse<>(false, e.getMessage(), null);
                return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
            } catch (Exception e) {
                ApiResponse<ContractType> apiResponse = new ApiResponse<>(false, e.getMessage(), null);
                return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/all")
        public ResponseEntity<ApiResponse<List<ContractType>>> getAllContractTypes() {
            ApiResponse<List<ContractType>> apiResponse = contractTypeService.getAllContractTypes();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK); // 200 OK
        }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContractType>> updateContractType(@PathVariable Long id, @RequestBody ContractTypeRequest contractTypeRequestRequest) {
        ApiResponse<ContractType> apiResponse = contractTypeService.updateContractType(id, contractTypeRequestRequest);
        return new ResponseEntity<>(apiResponse, apiResponse.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteContractType(@PathVariable Long id) {
        ApiResponse<String> apiResponse = contractTypeService.deleteContractType(id);
        return new ResponseEntity<>(apiResponse, apiResponse.success() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    }

