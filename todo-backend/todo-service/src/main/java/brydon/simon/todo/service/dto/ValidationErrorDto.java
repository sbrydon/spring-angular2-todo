package brydon.simon.todo.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDto {
    private final List<ValidationFieldErrorDto> validationErrors = new ArrayList<>();

    public List<ValidationFieldErrorDto> getValidationErrors() {
        return validationErrors;
    }

    public ValidationErrorDto() {
    }

    public ValidationErrorDto(ValidationFieldErrorDto error) {
        validationErrors.add(error);
    }

    public void addError(ValidationFieldErrorDto error) {
        validationErrors.add(error);
    }
}
