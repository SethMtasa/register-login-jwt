package seth.contract.dto.user;

public record ApiResponse<T>(boolean success, String message, T body) {
}
