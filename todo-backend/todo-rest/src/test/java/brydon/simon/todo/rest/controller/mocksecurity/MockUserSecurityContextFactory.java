package brydon.simon.todo.rest.controller.mocksecurity;

import brydon.simon.todo.service.security.SecurityUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockSecurityUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockSecurityUser user) {
        SecurityUser principal = mock(SecurityUser.class);
        when(principal.getId()).thenReturn(user.userId());

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.role()));
        when(principal.getAuthorities()).thenReturn(new HashSet<>(authorities));

        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal, user.password(), principal.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        return context;
    }
}
