package seth.contract.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seth.contract.dto.contract.ContractRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.Contract;
import seth.contract.service.ContractService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Contract>> createContract(@ModelAttribute ContractRequest contractRequest) {
        Contract createdContract = contractService.createContract(contractRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Contract created successfully", createdContract));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contract>> getContractById(@PathVariable Long id) {
        Optional<Contract> contract = contractService.getContractById(id);
        return contract.map(c -> ResponseEntity.ok(new ApiResponse<>(true, "Contract retrieved successfully", c)))
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "Contract not found", null)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Contract>>> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Contracts retrieved successfully", contracts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Contract>> updateContract(@PathVariable Long id, @RequestBody Contract updatedContract) {
        try {
            Contract contract = contractService.updateContract(id, updatedContract);
            return ResponseEntity.ok(new ApiResponse<>(true, "Contract updated successfully", contract));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Contract not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContract(@PathVariable Long id) {
        try {
            contractService.deleteContract(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Contract deleted successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(new ApiResponse<>(false, "Contract not found", null));
        }
    }
}