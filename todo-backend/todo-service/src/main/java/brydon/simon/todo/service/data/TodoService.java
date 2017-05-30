package brydon.simon.todo.service.data;

import brydon.simon.todo.data.model.Todo;
import brydon.simon.todo.data.repository.TodoRepository;
import brydon.simon.todo.service.data.mapping.TodoMapper;
import brydon.simon.todo.service.dto.TodoDto;
import brydon.simon.todo.service.dto.TodoPostDto;
import brydon.simon.todo.service.dto.TodoPatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.Clock;

@Service
public class TodoService {
    private final TodoRepository todoRepo;
    private final TodoMapper mapper;

    @Autowired
    public TodoService(TodoRepository todoRepo, TodoMapper mapper) {
        this.todoRepo = todoRepo;
        this.mapper = mapper;
    }

    public Page<TodoDto> findAll(TodoStatus status, int userId, Pageable pageable) {
        Page<Todo> result;

        switch (status) {
            case ACTIVE:
                result = todoRepo.findAllByUserIdAndDoneIsNull(userId, pageable);
                break;
            case DONE:
                result = todoRepo.findAllByUserIdAndDoneIsNotNull(userId, pageable);
                break;
            default:
                result = todoRepo.findAllByUserId(userId, pageable);
        }

        return result.map(mapper::toDto);
    }

    public TodoDto insert(int userId, TodoPostDto post) {
        Todo todo = new Todo(userId, post.getBody(), Clock.systemDefaultZone());
        todoRepo.save(todo);

        return mapper.toDto(todo);
    }

    public TodoDto update(int userId, int id, TodoPatchDto patch) throws ResourceNotFoundException {
        Todo todo = tryFindTodo(userId, id);

        if (patch.hasBody()) {
            todo.setBody(patch.getBody());
        }

        if (patch.hasDone()) {
            todo.setDone(patch.isDone());
        }

        todoRepo.save(todo);
        return mapper.toDto(todo);
    }

    public void delete(int userId, int id) throws ResourceNotFoundException {
        Todo todo = tryFindTodo(userId, id);
        todoRepo.delete(todo);
    }

    private Todo tryFindTodo(int userId, int id) throws ResourceNotFoundException {
        Todo todo = todoRepo.findByUserIdAndId(userId, id);
        if (todo == null) {
            String msg = "Todo(userId=%s,id=%s) could not be found";
            throw new ResourceNotFoundException(msg, userId, id);
        }

        return todo;
    }
}
