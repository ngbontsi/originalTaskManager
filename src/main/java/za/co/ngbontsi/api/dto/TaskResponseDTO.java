package za.co.ngbontsi.api.dto;
import za.co.ngbontsi.api.constant.TaskStatus;
import za.co.ngbontsi.api.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(  UUID id,
                                String title,
                                String description,
                                TaskStatus status,
                                LocalDate dueDate,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt) {
}
