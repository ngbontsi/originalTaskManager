package za.co.ngbontsi.api.controler;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.ngbontsi.api.dto.TaskRequestDTO;
import za.co.ngbontsi.api.dto.TaskResponseDTO;
import za.co.ngbontsi.api.mapper.TaskMapper;
import za.co.ngbontsi.api.model.Task;
import za.co.ngbontsi.api.response.EntityResponse;
import za.co.ngbontsi.api.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<EntityResponse<List<TaskResponseDTO>>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks()
                .stream()
                .map(TaskMapper::fromEntity)
                .toList();

        return ResponseEntity.ok(new EntityResponse<>(200, "Tasks retrieved successfully", tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<TaskResponseDTO>> getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(new EntityResponse<>(200, "Task found", TaskMapper.fromEntity(task))))
                .orElse(ResponseEntity.status(404).body(new EntityResponse<>(404, "Task not found")));
    }

    @PostMapping
    public ResponseEntity<EntityResponse<TaskResponseDTO>> createTask(@Valid @RequestBody TaskRequestDTO dto) {
        Task created = taskService.createTask(Task.builder()
                .title(dto.title())
                .description(dto.description())
                .dueDate(dto.dueDate())
                .status(dto.status())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new EntityResponse<>(201, "Task created", TaskMapper.fromEntity(created)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<TaskResponseDTO>> updateTask(@PathVariable UUID id, @Valid @RequestBody TaskRequestDTO dto) {
        try {
            Task updated = taskService.updateTask(id, Task.builder()
                    .title(dto.title())
                    .description(dto.description())
                    .dueDate(dto.dueDate())
                    .status(dto.status())
                    .build());

            return ResponseEntity.ok(new EntityResponse<>(200, "Task updated", TaskMapper.fromEntity(updated)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new EntityResponse<>(404, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<Void>> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new EntityResponse<>(200, "Task deleted"));
    }
}
