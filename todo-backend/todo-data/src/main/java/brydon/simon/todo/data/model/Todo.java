package brydon.simon.todo.data.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;

@Entity
public class Todo {
    private int userId;

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String body;

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime done;

    @Transient
    private final Clock clock;

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public LocalDateTime getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done ? LocalDateTime.now(clock) : null;
    }

    public Todo() {
        this.clock = Clock.systemDefaultZone();
    }

    public Todo(int userId, String body, Clock clock) {
        this.userId = userId;
        this.body = body;
        this.clock = clock;
    }

    @PrePersist
    protected void onInsert() {
        created = LocalDateTime.now(clock);
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now(clock);
    }
}
