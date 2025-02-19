package seth.contract.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.dto.contract.ContractRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.exception.ContractNotFoundException;
import seth.contract.exception.UnsupportedFileTypeException;
import seth.contract.model.Contract;
import seth.contract.service.ContractService;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    public ResponseEntity<ApiResponse<Contract>> createContract( @RequestParam("file") MultipartFile file,
                                                                 @ModelAttribute ContractRequest contractRequest) {
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

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getContractFile(@PathVariable Long id) {
        try {
            byte[] fileData = contractService.getContractFileById(id);
            String contentType = "application/pdf"; // Adjust based on file type

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"contract_" + id + ".pdf\"")
                    .body(fileData); // Return the raw byte array here
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // You can optionally return an error response in another way
        } catch (ContractNotFoundException | UnsupportedFileTypeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Same here
        }
    }

//    @GetMapping("/{id}/file")
//    public ResponseEntity<ApiResponse<byte[]>> getContractFile(@PathVariable Long id) {
//        try {
//            byte[] fileData = contractService.getContractFileById(id);
//            String contentType = "application/pdf"; // Adjust based on file type
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.valueOf(contentType))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"contract_" + id + ".pdf\"")
//                    .body(new ApiResponse<>(true, "Draw Result file retrieved successfully", fileData));
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponse<>(false, "Draw Result file not found", null));
//        } catch (ContractNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponse<>(false, "Draw Result not found", null));
//        }
//    }
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