package brydon.simon.todo.service.dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationDtoTest {
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validHasNoViolation() {
        RegistrationDto reg = new RegistrationDto("john.doe@example.org", "password");
        assertThat(validator.validate(reg)).isEmpty();
    }

    @Test
    public void usernameEmptyHasViolation() {
        RegistrationDto reg = new RegistrationDto("", "password");
        assertThat(validator.validate(reg)).hasSize(1);
    }

    @Test
    public void usernameNullHasViolation() {
        RegistrationDto reg = new RegistrationDto(null, "password");
        assertThat(validator.validate(reg)).hasSize(1);
    }

    @Test
    public void passwordEmptyHasViolation() {
        RegistrationDto reg = new RegistrationDto("john.doe@example.org", "");
        assertThat(validator.validate(reg)).hasSize(1);
    }

    @Test
    public void passwordNullHasViolation() {
        RegistrationDto reg = new RegistrationDto("john.doe@example.org", null);
        assertThat(validator.validate(reg)).hasSize(1);
    }
}
