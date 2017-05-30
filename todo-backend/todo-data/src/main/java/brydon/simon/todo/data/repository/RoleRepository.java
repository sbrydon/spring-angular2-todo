package brydon.simon.todo.data.repository;

import brydon.simon.todo.data.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
