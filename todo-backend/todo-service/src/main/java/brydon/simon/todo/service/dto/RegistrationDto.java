package brydon.simon.todo.service.dto;

import org.hibernate.validator.constraints.NotBlank;

public class RegistrationDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationDto() {
    }

    public RegistrationDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
