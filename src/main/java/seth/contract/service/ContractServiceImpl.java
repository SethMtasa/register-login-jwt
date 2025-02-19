package seth.contract.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import seth.contract.dto.contract.ContractRequest;
import seth.contract.exception.ContractNotFoundException;
import seth.contract.exception.UnsupportedFileTypeException;
import seth.contract.model.Contract;
import seth.contract.model.ContractType;
import seth.contract.model.Department;
import seth.contract.repository.ContractRepository;
import seth.contract.repository.ContractTypeRepository;
import seth.contract.repository.DepartmentRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final DepartmentRepository departmentRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

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
            // Generate a file path
            String filePath = uploadPath + "/" + file.getOriginalFilename();
            file.transferTo(new File(filePath)); // Save the file to the specified path
            contract.setFilePath(filePath);
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
    public byte[] getContractFileById(Long id) throws IOException, UnsupportedFileTypeException {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            String filePath = contract.getFilePath();

            // Check if the file path is valid
            if (filePath != null && !filePath.isEmpty()) {
                Path path = Paths.get(filePath);

                // Check if the file exists
                if (Files.exists(path)) {
                    // Get the file extension
                    String fileExtension = getFileExtension(path);

                    // Additional processing based on file type can be added here if necessary
                    switch (fileExtension.toLowerCase()) {
                        case "pdf":
                            // Handle PDF specific logic if needed
                            break;
                        case "docx":
                            // Handle DOCX specific logic if needed
                            break;
                        case "xlsx":
                            // Handle XLSX specific logic if needed
                            break;
                        default:
                            throw new UnsupportedFileTypeException("Unsupported file type: " + fileExtension);
                    }

                    // Return the file as a byte array
                    return Files.readAllBytes(path);
                } else {
                    throw new FileNotFoundException("Contract file not found at path: " + filePath);
                }
            } else {
                throw new FileNotFoundException("Contract file path not found for contract ID: " + id);
            }
        } else {
            throw new ContractNotFoundException("Contract not found for ID: " + id);
        }
    }

    // Helper method to get the file extension
    private String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0 && lastIndexOfDot < fileName.length() - 1) {
            return fileName.substring(lastIndexOfDot + 1);
        }
        return ""; // Return empty if no extension found
    }

//    @Override
//    public byte[] getContractFileById(Long id) throws IOException {
//        Optional<Contract> contractOptional = contractRepository.findById(id);
//        if (contractOptional.isPresent()) {
//            Contract contract = contractOptional.get();
//            String filePath = contract.getFilePath();
//            if (filePath != null && !filePath.isEmpty()) {
//                Path path = Paths.get(filePath);
//                if (Files.exists(path)) {
//                    return Files.readAllBytes(path);
//                } else {
//                    throw new FileNotFoundException("Contract file not found at path: " + filePath);
//                }
//            } else {
//                throw new FileNotFoundException("Contract file path not found for contract ID: " + id);
//            }
//        } else {
//            throw new ContractNotFoundException("Contract not found for ID: " + id);
//        }
//    }

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