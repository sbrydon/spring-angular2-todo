package brydon.simon.todo.rest.controller.mocksecurity;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockUserSecurityContextFactory.class)
public @interface WithMockSecurityUser {
    int userId();
    String role() default "ROLE_USER";
    String password() default "password";
}
