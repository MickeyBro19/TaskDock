package com.mickey.taskdock.service.impl;

import com.mickey.taskdock.dto.TaskRequest;
import com.mickey.taskdock.dto.TaskResponse;
import com.mickey.taskdock.entity.Task;
import com.mickey.taskdock.entity.enums.TaskStatus;
import com.mickey.taskdock.exception.TaskNotFoundException;
import com.mickey.taskdock.repo.TaskRepository;
import com.mickey.taskdock.service.TaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository taskRepository;
	
	@Override
	public TaskResponse createTask(TaskRequest request) {
		
		Task task = Task.builder()
				.taskName(request.taskName()).taskStatus(TaskStatus.PENDING).build();
		
		Task savedTask = taskRepository.save(task);
		
		return mapToResponse(savedTask);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskResponse> getAllTasks() {
		
		return taskRepository.findAll()
				.stream()
				.map(this :: mapToResponse)
				.toList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public TaskResponse getTaskById(Long id) {
		
		Task task = findTask(id);
		
		return mapToResponse(task);
	}
	
	@Override
	public TaskResponse updateTask(
			Long id,
			TaskRequest request
	                              ) {
		
		Task task = findTask(id);
		
		task.setTaskName(request.taskName());
		task.setTaskStatus(request.taskStatus());
		
		Task updatedTask = taskRepository.save(task);
		
		return mapToResponse(updatedTask);
	}
	
	@Override
	public void deleteTask(Long id) {
		
		Task task = findTask(id);
		
		taskRepository.delete(task);
	}
	
	private Task findTask(Long id) {
		
		return taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException(id));
	}
	
	private TaskResponse mapToResponse(Task task) {
		
		return new TaskResponse(
				task.getId(),
				task.getTaskName(),
				task.getTaskStatus(),
				task.getCreatedAt(),
				task.getUpdatedAt(),
				task.isComplete()
		);
	}
}
