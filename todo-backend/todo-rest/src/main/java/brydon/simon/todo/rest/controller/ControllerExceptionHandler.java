package brydon.simon.todo.rest.controller;

import brydon.simon.todo.service.data.ResourceNotFoundException;
import brydon.simon.todo.service.dto.ValidationErrorDto;
import brydon.simon.todo.service.dto.ValidationFieldErrorDto;
import brydon.simon.todo.service.security.UsernameExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto handleValidationException(MethodArgumentNotValidException e) {
        ValidationErrorDto dto = new ValidationErrorDto();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            dto.addError(new ValidationFieldErrorDto(error.getField(), error.getDefaultMessage()));
        }

        return dto;
    }

    @ExceptionHandler(UsernameExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto handleUsernameExistsException(UsernameExistsException e) {
        return new ValidationErrorDto(new ValidationFieldErrorDto("username", e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleNotFoundException(ResourceNotFoundException e) {
        logger.warn("Resource not found", e);
        return ResponseEntity.notFound().build();
    }
}
