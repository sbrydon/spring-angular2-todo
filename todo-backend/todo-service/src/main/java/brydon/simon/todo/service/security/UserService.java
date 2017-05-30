package brydon.simon.todo.service.security;


import brydon.simon.todo.data.model.Role;
import brydon.simon.todo.data.model.User;
import brydon.simon.todo.data.repository.RoleRepository;
import brydon.simon.todo.data.repository.UserRepository;
import brydon.simon.todo.service.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService, RegistrationService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        SecurityUser securityUser = user == null ? null : new SecurityUser(user);

        if (user == null || securityUser.getAuthorities().isEmpty()) {
            String msg = String.format("'%s' not found or has no granted authorities", username);
            throw new UsernameNotFoundException(msg);
        }

        return securityUser;
    }

    @Override
    public void register(String username, String password) throws UsernameExistsException {
        if (userRepo.findByUsername(username) != null) {
            throw new UsernameExistsException("'%s' already exists", username);
        }

        Role role = roleRepo.findByName("ROLE_USER");
        User user = new User(username, encoder.encode(password), role);
        userRepo.save(user);
    }
}
