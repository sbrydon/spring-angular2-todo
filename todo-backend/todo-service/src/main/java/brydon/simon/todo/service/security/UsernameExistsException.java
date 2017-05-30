package brydon.simon.todo.service.security;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String format, Object... args) {
        super(String.format(format, args));
    }
}
