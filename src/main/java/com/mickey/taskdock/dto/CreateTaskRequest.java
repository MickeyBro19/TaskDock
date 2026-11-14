package com.mickey.taskdock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
		@NotBlank(message = "Task name is required")
		@Size(max = 150, message = "Task name cannot exceed 150 characters")
		String taskName
) {
}
