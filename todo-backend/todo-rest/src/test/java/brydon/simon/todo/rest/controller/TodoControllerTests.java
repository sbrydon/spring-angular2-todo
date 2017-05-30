package brydon.simon.todo.rest.controller;

import brydon.simon.todo.rest.controller.mocksecurity.WithMockSecurityUser;
import brydon.simon.todo.service.data.TodoService;
import brydon.simon.todo.service.data.TodoStatus;
import brydon.simon.todo.service.dto.TodoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockSecurityUser(userId = 1)
public class TodoControllerTests {
    private final MediaType jsonContentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TodoService todoService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllDoneNullFindsAllTodos() throws Exception {
        when(todoService.findAll(eq(TodoStatus.ALL), eq(1), any())).thenReturn(getTodoPage());

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void getAllDoneTrueFindsDoneTodos() throws Exception {
        when(todoService.findAll(eq(TodoStatus.DONE), eq(1), any())).thenReturn(getTodoPage());

        mockMvc.perform(get("/todos?done=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void getAllDoneFalseFindsActiveTodos() throws Exception {
        when(todoService.findAll(eq(TodoStatus.ACTIVE), eq(1), any())).thenReturn(getTodoPage());

        mockMvc.perform(get("/todos?done=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void postInsertsTodo() throws Exception {
        when(todoService.insert(eq(1), any())).thenReturn(new TodoDto());

        mockMvc.perform(post("/todos")
                .content("{ \"body\": \"buy pencils\" }")
                .contentType(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void patchBodyUpdatesTodo() throws Exception {
        when(todoService.update(eq(1), eq(1), any())).thenReturn(new TodoDto());

        mockMvc.perform(patch("/todos/1")
                .content("{ \"body\": \"buy pens\" }")
                .contentType(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void patchDoneUpdatesTodo() throws Exception {
        when(todoService.update(eq(1), eq(1), any())).thenReturn(new TodoDto());

        mockMvc.perform(patch("/todos/1")
                .content("{ \"done\": true }")
                .contentType(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void patchBothUpdatesTodo() throws Exception {
        when(todoService.update(eq(1), eq(1), any())).thenReturn(new TodoDto());

        mockMvc.perform(patch("/todos/1")
                .content("{ \"body\": \"buy pens\", \"done\": true }")
                .contentType(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType));
    }

    @Test
    public void deleteDeletesTodo() throws Exception {
        doThrow(new RuntimeException()).when(todoService).delete(anyInt(), anyInt());
        doNothing().when(todoService).delete(eq(1), eq(1));

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNoContent());
    }

    private Page<TodoDto> getTodoPage() {
        return new PageImpl<>(new ArrayList<>(), new PageRequest(0, 1), 1);
    }
}
