package seth.contract.dto;

public record AuthenticationResponse<String>(boolean success, String message, String token) {
}
