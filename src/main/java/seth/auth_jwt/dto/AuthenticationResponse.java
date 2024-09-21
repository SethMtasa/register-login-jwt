package seth.auth_jwt.dto;

public record AuthenticationResponse<String>(boolean success, String message, String token) {
}
