package brydon.simon.todo.service.data.mapping;

import brydon.simon.todo.data.model.Todo;
import brydon.simon.todo.service.dto.TodoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    @Mappings({
            @Mapping(target = "done", expression = "java(todo.getDone() != null)"),
            @Mapping(target = "createdAt", source = "created"),
            @Mapping(target = "modifiedAt", source = "modified"),
            @Mapping(target = "doneAt", source = "done"),
    })
    TodoDto toDto(Todo todo);
}
