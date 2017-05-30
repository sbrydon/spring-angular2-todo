package brydon.simon.todo.rest.controller;

import brydon.simon.todo.service.data.TodoService;
import brydon.simon.todo.service.data.TodoStatus;
import brydon.simon.todo.service.dto.TodoDto;
import brydon.simon.todo.service.dto.TodoPatchDto;
import brydon.simon.todo.service.dto.TodoPostDto;
import brydon.simon.todo.service.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todos")
@SuppressWarnings("unchecked")
public class TodoController {
    private final TodoService todoService;
    private final TodoResourceAssembler todoAssembler;

    @Autowired
    public TodoController(TodoService todoService, TodoResourceAssembler todoAssembler) {
        this.todoService = todoService;
        this.todoAssembler = todoAssembler;
    }

    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<TodoDto> getAll(@AuthenticationPrincipal SecurityUser user,
                                          @RequestParam(value = "done", required = false) Boolean done,
                                          Pageable pageable,
                                          PagedResourcesAssembler assembler) {
        Page<TodoDto> page = todoService.findAll(TodoStatus.valueOf(done), user.getId(), pageable);
        return assembler.toResource(page, todoAssembler);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Resource<TodoDto> post(@AuthenticationPrincipal SecurityUser user,
                                  @Valid @RequestBody TodoPostDto post) {
        TodoDto todo = todoService.insert(user.getId(), post);
        return todoAssembler.toResource(todo);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public Resource<TodoDto> patch(@AuthenticationPrincipal SecurityUser user,
                                   @PathVariable int id,
                                   @Valid @RequestBody TodoPatchDto patch) {
        TodoDto todo = todoService.update(user.getId(), id, patch);
        return todoAssembler.toResource(todo);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal SecurityUser user,
                                 @PathVariable int id) {
        todoService.delete(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
