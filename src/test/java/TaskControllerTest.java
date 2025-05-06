import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import za.co.ngbontsi.api.Main;
import za.co.ngbontsi.api.constant.TaskStatus;
import za.co.ngbontsi.api.dto.TaskRequestDTO;
import za.co.ngbontsi.api.model.Task;
import za.co.ngbontsi.api.service.TaskService;
import za.co.ngbontsi.api.controler.TaskController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = Main.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    /*@Autowired
    private ObjectMapper objectMapper;

     */
    private final ObjectMapper objectMapper = new ObjectMapper();;

    @Test
    void shouldReturnAllTasks() throws Exception {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title("Read book")
                .description("Learn Spring Boot")
                .status(TaskStatus.IN_PROGRESS)
                .dueDate(LocalDate.now().plusDays(3))
                .build();

        when(taskService.getAllTasks()).thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Read book"))
                .andExpect(jsonPath("$.data[0].status").value("IN_PROGRESS"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        TaskRequestDTO requestDTO = new TaskRequestDTO("New Task", "Test create", TaskStatus.NOT_STARTED, LocalDate.now().plusDays(5));

        Task createdTask = Task.builder()
                .id(UUID.randomUUID())
                .title(requestDTO.title())
                .description(requestDTO.description())
                .status(requestDTO.status())
                .dueDate(requestDTO.dueDate())
                .build();

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("New Task"));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsMissing() throws Exception {
        TaskRequestDTO invalidDto = new TaskRequestDTO(
                "", // Empty title
                "No title",
                TaskStatus.NOT_STARTED,
                LocalDate.now().plusDays(2)
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenDueDateIsInPast() throws Exception {
        TaskRequestDTO invalidDto = new TaskRequestDTO(
                "Past Task",
                "This task has a past due date",
                TaskStatus.NOT_STARTED,
                LocalDate.now().minusDays(1) // Invalid
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void shouldUpdateTask() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = Task.builder()
                .id(id)
                .title("Updated Task")
                .description("Updated desc")
                .status(TaskStatus.COMPLETED)
                .dueDate(LocalDate.now().plusDays(2))
                .build();

        when(taskService.updateTask(eq(id), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated Task"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(taskService).deleteTask(id);

        mockMvc.perform(delete("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task deleted"));
    }


}
