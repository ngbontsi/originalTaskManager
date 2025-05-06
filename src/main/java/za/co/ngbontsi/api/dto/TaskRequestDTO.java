package za.co.ngbontsi.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import za.co.ngbontsi.api.constant.TaskStatus;

import java.time.LocalDate;

public record TaskRequestDTO(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Status is required")
        TaskStatus status,

        @FutureOrPresent(message = "Due date must be in the present or future")
        LocalDate dueDate
) {}
