package seth.contract.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import seth.contract.dto.AuthenticationResponse;
import seth.contract.dto.LoginRequest;
import seth.contract.dto.RegistrationRequest;
import seth.contract.dto.user.ApiResponse;
import seth.contract.model.Role;
import seth.contract.model.User;
import seth.contract.repository.RoleRepository;
import seth.contract.repository.UserRepository;
import seth.contract.security.JwtService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Override
    public AuthenticationResponse<String> authenticateUser(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );
        Optional<User> user = userRepository.findByUsername(loginRequest.username());
        if (user.isPresent()){
            String token = jwtService.generateToken(user.get());
            return new AuthenticationResponse<>(true, "success", token);
        }
        return new AuthenticationResponse<>(false, "failed", null);
    }

    @Override
    public AuthenticationResponse<String> registerUser(RegistrationRequest registrationRequest) {
        String username = registrationRequest.username();
        String email = registrationRequest.email();

        AuthenticationResponse<String> validationResponse = validateUser(username, email);

        if (!validationResponse.success()) {

            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent() && !existingUser.get().isActiveStatus()) {
                User user = existingUser.get();
                user.setActiveStatus(true);
                userRepository.save(user);
                return new AuthenticationResponse<>(true, "User activated successfully.", null);
            } else {
                return validationResponse;
            }
        } else {

            User user = new User();
            user.setFirstName(registrationRequest.firstName());
            user.setLastName(registrationRequest.lastName());
            user.setEmail(email);
            user.setUsername(username);
            user.setRole(roleRepository.findByName("USER"));

            try {
                userRepository.save(user);
                return new AuthenticationResponse<>(true, "User created successfully.", null);
            } catch (Exception e) {
                return new AuthenticationResponse<>(false, "Failed to register user: " + e.getMessage(), null);
            }
        }
    }

    @Override
    public ApiResponse<String> deleteUser(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setActiveStatus(false);
                userRepository.save(user); // Save the updated user
                return new ApiResponse<>(true, "User deleted successfully.", null);
            } else {
                return new ApiResponse<>(false, "User not found.", null);
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error deleting user: " + e.getMessage(), null);
        }
    }

    @Override
    public ApiResponse<User> getUserByUsername(String username, boolean activeStatus) {Optional<User> optionalUser = userRepository.findByUsernameAndActiveStatus(username, true); // Check for activeStatus == true
        if (optionalUser.isPresent()) {
            return new ApiResponse<>(true, "User found", optionalUser.get());
        } else {
            return new ApiResponse<>(false, "User not found with username: " + username, null);
        }
    }

    @Override
    public ApiResponse<List<User>> getAllActiveUsers() {
        List<User> activeUsers = userRepository.findByActiveStatus(true);
        if (!activeUsers.isEmpty()) {
            return new ApiResponse<>(true, "Active users retrieved successfully", activeUsers);
        } else {
            return new ApiResponse<>(false, "No active users found", null);
        }
    }

    @Override
    public ApiResponse<User> updateUser(Long id, RegistrationRequest updateRequest) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();


                if (updateRequest.firstName() != null) {
                    user.setFirstName(updateRequest.firstName());
                }
                if (updateRequest.lastName() != null) {
                    user.setLastName(updateRequest.lastName());
                }
                if (updateRequest.email() != null) {
                    user.setEmail(updateRequest.email());
                }
                if (updateRequest.username() != null) {
                    user.setUsername(updateRequest.username());
                }


                if (updateRequest.role() != null && updateRequest.role() != null) {
                    Role newRole = roleRepository.findByName(updateRequest.role());
                    if (newRole == null) {
                        return new ApiResponse<>(false, "Invalid role provided.", null);
                    }
                    user.setRole(newRole);
                }

                userRepository.save(user);
                return new ApiResponse<>(true, "User updated successfully.",user );
            } else {
                return new ApiResponse<>(false, "User not found.", null);
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error updating user: " + e.getMessage(), null);
        }
    }

    private AuthenticationResponse<String> validateUser(String username, String email) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) return new AuthenticationResponse<>(false,"Username already exist.", null);
        Optional<User> emailUser = userRepository.findByEmail(email);
        if (emailUser.isPresent()) return new AuthenticationResponse<>(false,"Email already exist.", null);
        return new AuthenticationResponse<>(true, "", null);
    }

}
