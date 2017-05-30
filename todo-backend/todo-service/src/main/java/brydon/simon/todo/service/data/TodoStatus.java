package brydon.simon.todo.service.data;

public enum TodoStatus {
    ALL,
    ACTIVE,
    DONE;

    public static TodoStatus valueOf(Boolean done) {
        if (done == null) {
            return ALL;
        }

        return done ? DONE : ACTIVE;
    }
}
