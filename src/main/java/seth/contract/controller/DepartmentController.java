package seth.contract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seth.contract.dto.contract.DepartmentRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.Department;
import seth.contract.service.DepartmentService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Department>> addNewDepartment(@RequestBody DepartmentRequest departmentRequest) {
        try {
            ApiResponse<Department> apiResponse = departmentService.addNewDepartment(departmentRequest);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<Department> apiResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<Department> apiResponse = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        ApiResponse<List<Department>> apiResponse = departmentService.getAllDepartments();
        return new ResponseEntity<>(apiResponse, apiResponse.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequest departmentRequest) {
        ApiResponse<Department> apiResponse = departmentService.updateDepartment(id, departmentRequest);
        return new ResponseEntity<>(apiResponse, apiResponse.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Long id) {
        ApiResponse<String> apiResponse = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(apiResponse, apiResponse.success() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
