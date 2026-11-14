package com.mickey.taskdock.controller;

import com.mickey.taskdock.dto.CreateTaskRequest;
import com.mickey.taskdock.dto.UpdateTaskRequest;
import com.mickey.taskdock.dto.TaskResponse;
import com.mickey.taskdock.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
	
	private final TaskService taskService;
	
	@PostMapping
	public ResponseEntity<TaskResponse> createTask(
			@Valid @RequestBody CreateTaskRequest request
	                                              ) {
		
		TaskResponse response = taskService.createTask(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<TaskResponse>> getAllTasks() {
		
		return ResponseEntity.ok(taskService.getAllTasks());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getTaskById(
			@PathVariable Long id
	                                               ) {
		
		return ResponseEntity.ok(taskService.getTaskById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(
			@PathVariable Long id,
			@Valid @RequestBody UpdateTaskRequest request
	                                              ) {
		
		return ResponseEntity.ok(
				taskService.updateTask(id, request)
		                        );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(
			@PathVariable Long id
	                                      ) {
		
		taskService.deleteTask(id);
		
		return ResponseEntity.noContent().build();
	}
}