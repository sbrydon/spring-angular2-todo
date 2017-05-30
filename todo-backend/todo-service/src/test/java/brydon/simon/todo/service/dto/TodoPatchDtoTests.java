package brydon.simon.todo.service.dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoPatchDtoTests {
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validHasNoViolation() {
        TodoPatchDto patch = new TodoPatchDto("updated", true);
        assertThat(validator.validate(patch)).isEmpty();
    }

    @Test
    public void bodyEmptyHasViolation() {
        TodoPatchDto patch = new TodoPatchDto("", true);
        assertThat(validator.validate(patch)).hasSize(1);
    }

    @Test
    public void bodyAndDoneSetHasBothIsTrue() {
        TodoPatchDto patch = new TodoPatchDto("updated", true);
        assertThat(patch.hasBody() && patch.hasDone()).isTrue();
    }

    @Test
    public void bodySetHasBodyIsTrueHasDoneIsFalse() {
        TodoPatchDto patch = new TodoPatchDto("updated", null);

        assertThat(patch.hasBody()).isTrue();
        assertThat(patch.hasDone()).isFalse();
    }

    @Test
    public void doneSetHasDoneIsTrueHasBodyIsFalse() {
        TodoPatchDto patch = new TodoPatchDto(null, true);

        assertThat(patch.hasDone()).isTrue();
        assertThat(patch.hasBody()).isFalse();
    }
}
