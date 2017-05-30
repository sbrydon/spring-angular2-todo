package brydon.simon.todo.service.dto;

public class ValidationFieldErrorDto {
    private final String field;
    private final String message;

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public ValidationFieldErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
