package brydon.simon.todo.data.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoTests {
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validHasNoViolation() {
        Todo todo = new Todo(1, "buy pencils", Clock.systemDefaultZone());
        assertThat(validator.validate(todo)).isEmpty();
    }

    @Test
    public void bodyEmptyHasViolation() {
        Todo todo = new Todo(1, "", Clock.systemDefaultZone());
        assertThat(validator.validate(todo)).hasSize(1);
    }

    @Test
    public void bodyNullHasViolation() {
        Todo todo = new Todo(1, null, Clock.systemDefaultZone());
        assertThat(validator.validate(todo)).hasSize(1);
    }

    @Test
    public void onInsertSetsCreatedToNow() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Todo todo = new Todo(1, "buy pencils", clock);
        todo.onInsert();

        assertThat(todo.getCreated()).isEqualTo(LocalDateTime.now(clock));
    }

    @Test
    public void onUpdateSetsModifiedToNow() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Todo todo = new Todo(1, "buy pencils", clock);
        todo.onUpdate();

        assertThat(todo.getModified()).isEqualTo(LocalDateTime.now(clock));
    }

    @Test
    public void setDoneTrueOrFalseSetsDoneToNowOrNull() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Todo todo = new Todo(1, "buy pencils", clock);
        todo.setDone(true);

        assertThat(todo.getDone()).isEqualTo(LocalDateTime.now(clock));

        todo.setDone(false);

        assertThat(todo.getDone()).isNull();
    }
}
