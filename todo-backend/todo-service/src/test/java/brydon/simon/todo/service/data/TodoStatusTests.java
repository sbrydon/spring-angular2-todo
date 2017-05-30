package brydon.simon.todo.service.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoStatusTests {
    @Test
    public void valueOfNullEqualsAll() {
        assertThat(TodoStatus.valueOf((Boolean) null)).isEqualTo(TodoStatus.ALL);
    }

    @Test
    public void valueOfTrueEqualsDone() {
        assertThat(TodoStatus.valueOf(true)).isEqualTo(TodoStatus.DONE);
    }

    @Test
    public void valueOfFalseEqualsActive() {
        assertThat(TodoStatus.valueOf(false)).isEqualTo(TodoStatus.ACTIVE);
    }
}
