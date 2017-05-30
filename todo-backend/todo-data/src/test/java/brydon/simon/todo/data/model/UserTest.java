package brydon.simon.todo.data.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.*;

public class UserTest {
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validHasNoViolation() {
        User user = new User("john.doe@example.org", "password", new Role());
        assertThat(validator.validate(user)).isEmpty();
    }

    @Test
    public void usernameEmptyHasViolation() {
        User user = new User("", "password", new Role());
        assertThat(validator.validate(user)).hasSize(1);
    }

    @Test
    public void usernameNullHasViolation() {
        User user = new User(null, "password", new Role());
        assertThat(validator.validate(user)).hasSize(1);
    }

    @Test
    public void passwordEmptyHasViolation(){
        User user = new User("john.doe@example.org", "", new Role());
        assertThat(validator.validate(user)).hasSize(1);
    }

    @Test
    public void passwordNullHasViolation(){
        User user = new User("john.doe@example.org", null, new Role());
        assertThat(validator.validate(user)).hasSize(1);
    }

    @Test
    public void rolesEmptyHasViolation() {
        User user = new User("john.doe@example.org", "password");
        assertThat(validator.validate(user)).hasSize(1);
    }
}
