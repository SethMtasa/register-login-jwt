package seth.contract.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import seth.contract.dto.contract.DepartmentRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.Department;
import seth.contract.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public ApiResponse<Department> addNewDepartment(DepartmentRequest departmentRequest) throws Exception {
        try {
            Department department = new Department();
            department.setName(departmentRequest.name());
            department.setActiveStatus(true); // Set active status to true by default
            Department savedDepartment = departmentRepository.save(department);
            return new ApiResponse<>(true,  "Department added successfully",department);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Department with this name already exists.", e);
        } catch (Exception e) {
            throw new Exception("Failed to add department: " + e.getMessage(), e);
        }
    }

    @Override

        public ApiResponse<List<Department>> getAllDepartments() {
            try {
                List<Department> departments = departmentRepository.findAll();
                return new ApiResponse<>(true, "Departments retrieved successfully", departments);
            } catch (Exception e) {
                return new ApiResponse<>(false,  "Failed to retrieve departments: " + e.getMessage(), List.of()); //Return empty list on error
            }
        }


    @Override
    public ApiResponse<Department> updateDepartment(Long id, DepartmentRequest departmentRequest) {
        {
            try {
                Optional<Department> optionalDepartment = departmentRepository.findById(id);

                if (optionalDepartment.isPresent()) {
                    Department department = optionalDepartment.get();
                    department.setName(departmentRequest.name()); // Update only the name for simplicity.  You can add more fields as needed.
                    Department updatedDepartment = departmentRepository.save(department);
                    return new ApiResponse<>(true, "Department updated successfully", updatedDepartment);
                } else {
                    return new ApiResponse<>(false, "Department not found", null);
                }
            } catch (Exception e) {
                return new ApiResponse<>(false, "Failed to update department: " + e.getMessage(), null);
            }
        }
    }

    @Override
    public ApiResponse<String> deleteDepartment(Long id) {
        try {
            Optional<Department> optionalDepartment = departmentRepository.findById(id);
            if (optionalDepartment.isPresent()) {
                departmentRepository.deleteById(id);
                return new ApiResponse<>(true, "Department deleted successfully", null);
            } else {
                return new ApiResponse<>(false, "Department not found", null);
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, "Failed to delete department: " + e.getMessage(), null);
        }
    }
    }

