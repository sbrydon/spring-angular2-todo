package brydon.simon.todo.data.repository;

import brydon.simon.todo.data.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Integer> {
    Page<Todo> findAllByUserId(int userId, Pageable pageable);
    Page<Todo> findAllByUserIdAndDoneIsNull(int userId, Pageable pageable);
    Page<Todo> findAllByUserIdAndDoneIsNotNull(int userId, Pageable pageable);
    Todo findByUserIdAndId(int userId, int id);
}
