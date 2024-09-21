package seth.auth_jwt.dto;

public record ApiResponse<T>(boolean success, String message, T body) {
}
