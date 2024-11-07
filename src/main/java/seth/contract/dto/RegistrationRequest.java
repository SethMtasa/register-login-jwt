package seth.contract.dto;

public record RegistrationRequest(String firstName,
                                  String lastName,
                                  String email,
                                  String username,
                                  String role
                                 ) {
}
