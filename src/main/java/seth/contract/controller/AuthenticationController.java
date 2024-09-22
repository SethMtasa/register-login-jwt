package seth.contract.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seth.contract.dto.AuthenticationResponse;
import seth.contract.dto.LoginRequest;
import seth.contract.dto.RegistrationRequest;
import seth.contract.service.UserService;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse<String>> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse<String>> registerUser(@RequestBody RegistrationRequest registrationRequest) throws Exception {
        return ResponseEntity.ok(userService.registerUser(registrationRequest));
    }
}
