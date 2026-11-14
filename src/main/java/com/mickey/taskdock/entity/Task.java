package com.mickey.taskdock.entity;

import com.mickey.taskdock.entity.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "task_name", nullable = false, length = 150)
	private String taskName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "task_status", nullable = false, length = 30)
	private TaskStatus taskStatus;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@Column(name = "is_complete", nullable = false)
	private boolean isComplete;
	
	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		
		createdAt = now;
		updatedAt = now;
		
		syncCompletionStatus();
	}
	
	private void syncCompletionStatus() {
		isComplete = taskStatus == TaskStatus.COMPLETED;
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
		
		syncCompletionStatus();
	}
	
}