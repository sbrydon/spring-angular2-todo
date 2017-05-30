package brydon.simon.todo.service.security;

public interface RegistrationService {
    void register(String username, String password) throws UsernameExistsException;
}
