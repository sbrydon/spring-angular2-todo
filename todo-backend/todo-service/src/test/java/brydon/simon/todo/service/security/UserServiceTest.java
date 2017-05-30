package brydon.simon.todo.service.security;

import brydon.simon.todo.data.model.Role;
import brydon.simon.todo.data.model.User;
import brydon.simon.todo.data.repository.RoleRepository;
import brydon.simon.todo.data.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private User user;

    @Mock
    private Role role;

    private UserService userService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        userService = new UserService(userRepo, roleRepo, encoder);
    }

    @Test
    public void loadUserFoundReturnsResult() {
        when(role.getName()).thenReturn("ROLE_USER");
        when(user.getRoles()).thenReturn(new HashSet<>(Collections.singletonList(role)));
        when(userRepo.findByUsername("john.doe@example.org")).thenReturn(user);

        assertThat(userService.loadUserByUsername("john.doe@example.org")).isNotNull();
    }

    @Test
    public void loadUserNotFoundThrowsUsernameNotFound()  {
        when(userRepo.findByUsername("john.doe@example.org")).thenReturn(null);

        exception.expect(UsernameNotFoundException.class);
        userService.loadUserByUsername("john.doe@example.org");
    }

    @Test
    public void loadUserHasNoRolesThrowsUsernameNotFound() {
        when(user.getRoles()).thenReturn(new HashSet<>());
        when(userRepo.findByUsername("john.doe@example.org")).thenReturn(user);

        exception.expect(UsernameNotFoundException.class);
        userService.loadUserByUsername("john.doe@example.org");
    }

    @Test
    public void registerSavesUserWithUserRole() {
        when(userRepo.findByUsername("john.doe@example.org")).thenReturn(null);
        when(role.getName()).thenReturn("ROLE_USER");
        when(roleRepo.findByName("ROLE_USER")).thenReturn(role);

        userService.register("john.doe@example.org", "password");

        ArgumentCaptor<User> arg = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(arg.capture());
        assertThat(arg.getValue().getRoles()).containsExactly(role);
    }

    @Test
    public void registerSavesUserWithEncodedPassword() {
        when(userRepo.findByUsername("john.doe@example.org")).thenReturn(null);
        when(encoder.encode("password")).thenReturn("encoded");

        userService.register("john.doe@example.org", "password");

        ArgumentCaptor<User> arg = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(arg.capture());
        assertThat(arg.getValue().getPassword()).isEqualTo("encoded");
    }

    @Test
    public void registerUserFoundThrowsUsernameExists() {
        when(userRepo.findByUsername("john.doe@example.org")).thenReturn(user);

        exception.expect(UsernameExistsException.class);
        userService.register("john.doe@example.org", "password");
    }
}
