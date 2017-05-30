package brydon.simon.todo.service.dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoPostDtoTests {
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validHasNoViolation() {
        TodoPostDto post = new TodoPostDto("buy pencils");
        assertThat(validator.validate(post)).isEmpty();
    }

    @Test
    public void bodyEmptyHasViolation() {
        TodoPostDto post = new TodoPostDto("");
        assertThat(validator.validate(post)).hasSize(1);
    }

    @Test
    public void bodyNullHasViolation() {
        TodoPostDto post = new TodoPostDto(null);
        assertThat(validator.validate(post)).hasSize(1);
    }
}
