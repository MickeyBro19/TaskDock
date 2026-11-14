package com.mickey.taskdock.service;

import com.mickey.taskdock.dto.CreateTaskRequest;
import com.mickey.taskdock.dto.UpdateTaskRequest;
import com.mickey.taskdock.dto.TaskResponse;

import java.util.List;

public interface TaskService {
	
	TaskResponse createTask(CreateTaskRequest request);
	
	List<TaskResponse> getAllTasks();
	
	TaskResponse getTaskById(Long id);
	
	TaskResponse updateTask(Long id, UpdateTaskRequest request);
	
	void deleteTask(Long id);
	
}
