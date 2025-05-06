package za.co.ngbontsi.api.service;

import org.springframework.stereotype.Service;
import za.co.ngbontsi.api.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.ngbontsi.api.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(UUID id, Task task) {
        return taskRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(task.getTitle());
                    existing.setDescription(task.getDescription());
                    existing.setStatus(task.getStatus());
                    existing.setDueDate(task.getDueDate());
                    return taskRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
