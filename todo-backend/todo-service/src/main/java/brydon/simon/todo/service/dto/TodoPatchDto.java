package brydon.simon.todo.service.dto;

import javax.validation.constraints.Size;

public class TodoPatchDto {
    @Size(min = 1)
    private String body;

    private Boolean done;

    public String getBody() {
        return body;
    }

    public Boolean isDone() {
        return done;
    }

    public boolean hasBody() {
        return body != null;
    }

    public boolean hasDone() {
        return done != null;
    }

    public TodoPatchDto() {
    }

    public TodoPatchDto(String body, Boolean done) {
        this.body = body;
        this.done = done;
    }
}
