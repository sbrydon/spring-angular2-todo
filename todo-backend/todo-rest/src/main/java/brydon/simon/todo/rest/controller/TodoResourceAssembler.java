package brydon.simon.todo.rest.controller;

import brydon.simon.todo.service.dto.TodoDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class TodoResourceAssembler extends ResourceAssemblerSupport<TodoDto, Resource> {
    public TodoResourceAssembler() {
        super(TodoController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends TodoDto> todos) {
        List<Resource> resources = new ArrayList<>();
        for(TodoDto todo : todos) {
            resources.add(toResource(todo));
        }

        return resources;
    }

    @Override
    public Resource toResource(TodoDto todo) {
        Link link = linkTo(TodoController.class).slash(todo.getId()).withSelfRel();
        return new Resource<>(todo, link);
    }
}
