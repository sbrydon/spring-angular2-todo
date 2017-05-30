package brydon.simon.todo.service.dto;

import org.hibernate.validator.constraints.NotBlank;

public class TodoPostDto {
    @NotBlank
    private String body;

    public String getBody() {
        return body;
    }

    public TodoPostDto() {
    }

    public TodoPostDto(String body) {
        this.body = body;
    }
}
