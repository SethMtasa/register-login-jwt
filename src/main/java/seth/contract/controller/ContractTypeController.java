package seth.contract.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seth.contract.dto.contract.ContractTypeRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.ContractType;
import seth.contract.service.ContractTypeService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contractTypes") // Endpoint base URL
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
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED); // 201 Created
            } catch (DataIntegrityViolationException e) {
                ApiResponse<ContractType> apiResponse = new ApiResponse<>(false, e.getMessage(), null);
                return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT); // 409 Conflict
            } catch (Exception e) {
                ApiResponse<ContractType> apiResponse = new ApiResponse<>(false, e.getMessage(), null);
                return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            }
        }

        @GetMapping("/all")
        public ResponseEntity<ApiResponse<List<ContractType>>> getAllContractTypes() {
            ApiResponse<List<ContractType>> apiResponse = contractTypeService.getAllContractTypes();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK); // 200 OK
        }
    }

