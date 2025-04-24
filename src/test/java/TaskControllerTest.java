import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import za.co.ngbontsi.api.constant.TaskStatus;
import za.co.ngbontsi.api.controler.TaskController;
import za.co.ngbontsi.api.model.Task;
import za.co.ngbontsi.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void shouldReturnAllTasks() throws Exception {
        UUID id = UUID.randomUUID();
        List<Task> mockTasks = List.of(new Task(id, "Test task","check", TaskStatus.NOT_STARTED,LocalDate.now(), LocalDateTime.now(), LocalDateTime.now()));

        Mockito.when(taskService.getAllTasks()).thenReturn(mockTasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Tasks retrieved"))
                .andExpect(jsonPath("$.data").isArray());
    }
}
