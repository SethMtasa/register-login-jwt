package seth.contract.service;


import seth.contract.dto.AuthenticationResponse;
import seth.contract.dto.LoginRequest;
import seth.contract.dto.RegistrationRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.User;

import java.util.List;

public interface UserService {
    AuthenticationResponse<String> authenticateUser(LoginRequest loginRequest);
    AuthenticationResponse<String> registerUser(RegistrationRequest registrationRequest) throws Exception;

    ApiResponse<String> deleteUser(Long id);
    ApiResponse<User> getUserByUsername(String username, boolean activeStatus);

    ApiResponse<List<User>> getAllActiveUsers();
    ApiResponse<User> updateUser(Long id, RegistrationRequest editUserRequest);

}
