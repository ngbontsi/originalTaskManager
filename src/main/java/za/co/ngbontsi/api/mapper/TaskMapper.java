package za.co.ngbontsi.api.mapper;

import za.co.ngbontsi.api.dto.TaskResponseDTO;
import za.co.ngbontsi.api.model.Task;

public class TaskMapper {

    public static TaskResponseDTO fromEntity(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
