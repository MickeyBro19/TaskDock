package com.mickey.taskdock.repo;

import com.mickey.taskdock.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository
		extends JpaRepository<Task, Long> {
	
}
