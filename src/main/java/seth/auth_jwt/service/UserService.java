package seth.auth_jwt.service;


import seth.auth_jwt.dto.AuthenticationResponse;
import seth.auth_jwt.dto.LoginRequest;
import seth.auth_jwt.dto.RegistrationRequest;

public interface UserService {
    AuthenticationResponse<String> authenticateUser(LoginRequest loginRequest);
    AuthenticationResponse<String> registerUser(RegistrationRequest registrationRequest) throws Exception;
 }
