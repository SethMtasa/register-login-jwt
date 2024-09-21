package seth.auth_jwt.dto;

public record RegistrationRequest(String firstName,
                                  String lastName,
                                  String email,
                                  String username,
                                  String password,
                                  String jobTitle,
                                  String department,
                                  String grade,
                                  String cellNumber) {
}
