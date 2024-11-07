package seth.contract.service;

import seth.contract.dto.contract.DepartmentRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.Department;

import java.util.List;

public interface DepartmentService {

    ApiResponse<Department> addNewDepartment(DepartmentRequest departmentRequest) throws  Exception;
    ApiResponse<List<Department>> getAllDepartments();
    ApiResponse<Department> updateDepartment(Long id, DepartmentRequest departmentRequest) ;
    ApiResponse<String> deleteDepartment(Long id) ;

}
