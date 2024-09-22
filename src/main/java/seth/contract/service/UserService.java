package seth.contract.service;


import seth.contract.dto.AuthenticationResponse;
import seth.contract.dto.LoginRequest;
import seth.contract.dto.RegistrationRequest;

public interface UserService {
    AuthenticationResponse<String> authenticateUser(LoginRequest loginRequest);
    AuthenticationResponse<String> registerUser(RegistrationRequest registrationRequest) throws Exception;
 }
