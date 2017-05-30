package brydon.simon.todo.service.data;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String format, Object... args) {
        super(String.format(format, args));
    }
}
