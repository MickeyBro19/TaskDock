package com.mickey.taskdock.dto;

import com.mickey.taskdock.entity.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
		
		Long id,
		String taskName,
		TaskStatus taskStatus,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		boolean isComplete

) {
}
