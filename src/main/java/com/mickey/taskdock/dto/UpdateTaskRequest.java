package com.mickey.taskdock.dto;

import com.mickey.taskdock.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
		
		@NotBlank(message = "Task name is required")
		@Size(max = 150, message = "Task name cannot exceed 150 characters")
		String taskName,
		
		@NotNull(message = "Task status is required")
		TaskStatus taskStatus

) {
}
