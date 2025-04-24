package za.co.ngbontsi.api.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<EntityResponse<List<Task>>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(new EntityResponse<>(200, "Tasks retrieved successfully", tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<Task>> getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(new EntityResponse<>(200, "Task found", task)))
                .orElse(ResponseEntity.status(404).body(new EntityResponse<>(404, "Task not found")));
    }

    @PostMapping
    public ResponseEntity<EntityResponse<Task>> createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(201).body(new EntityResponse<>(201, "Task created", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<Task>> updateTask(@PathVariable UUID id, @RequestBody Task task) {
        try {
            Task updated = taskService.updateTask(id, task);
            return ResponseEntity.ok(new EntityResponse<>(200, "Task updated", updated));
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
