package seth.auth_jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import seth.auth_jwt.dto.AuthenticationResponse;
import seth.auth_jwt.dto.LoginRequest;
import seth.auth_jwt.dto.RegistrationRequest;
import seth.auth_jwt.model.User;
import seth.auth_jwt.repository.RoleRepository;
import seth.auth_jwt.repository.UserRepository;
import seth.auth_jwt.security.JwtService;

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
        User user = new User();
        user.setFirstName(registrationRequest.firstName());
        user.setLastName(registrationRequest.lastName());
        user.setEmail(registrationRequest.email());
        user.setUsername(registrationRequest.username());
        user.setPassword(new BCryptPasswordEncoder().encode(registrationRequest.password()));
        user.setRole(roleRepository.findByName("USER"));
        AuthenticationResponse<String> validate = validateUser(registrationRequest.username(), registrationRequest.email());
        if (validate.success()) {
            try {
                userRepository.save(user);
                //Generate token
               // String token = jwtService.generateToken(user);
                return new AuthenticationResponse<>(true, "User created.", null);
            } catch (Exception e) {
                return new AuthenticationResponse<>(false, "Failed to register user " + e, null);
            }
        }
        return validate;
    }

    private AuthenticationResponse<String> validateUser(String username, String email) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) return new AuthenticationResponse<>(false,"Username already exist.", null);
        Optional<User> emailUser = userRepository.findByEmail(email);
        if (emailUser.isPresent()) return new AuthenticationResponse<>(false,"Email already exist.", null);
        return new AuthenticationResponse<>(true, "", null);
    }

}
