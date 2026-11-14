package com.mickey.taskdock.service;

import com.mickey.taskdock.dto.TaskRequest;
import com.mickey.taskdock.dto.TaskResponse;

import java.util.List;

public interface TaskService {
	
	TaskResponse createTask(TaskRequest request);
	
	List<TaskResponse> getAllTasks();
	
	TaskResponse getTaskById(Long id);
	
	TaskResponse updateTask(Long id, TaskRequest request);
	
	void deleteTask(Long id);
	
}
