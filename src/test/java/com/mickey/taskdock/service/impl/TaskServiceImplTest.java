package com.mickey.taskdock.service.impl;

import com.mickey.taskdock.dto.CreateTaskRequest;
import com.mickey.taskdock.dto.UpdateTaskRequest;
import com.mickey.taskdock.dto.TaskResponse;
import com.mickey.taskdock.entity.Task;
import com.mickey.taskdock.entity.enums.TaskStatus;
import com.mickey.taskdock.exception.TaskNotFoundException;
import com.mickey.taskdock.repo.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskServiceImpl taskService;
	
	private Task task;
	
	@BeforeEach
	void setUp() {
		task = Task.builder()
				.taskName("Learn Docker")
				.taskStatus(TaskStatus.PENDING)
				.build();
	}
	
	
	@Test
	void createTaskShouldReturnSavedTask() {
		
		when(taskRepository.save(any(Task.class)))
				.thenAnswer(invocation ->
						            invocation.getArgument(0)
				           );
		
		TaskResponse response = taskService.createTask(
				new CreateTaskRequest("Learn Docker")
		                                              );
		
		assertEquals("Learn Docker", response.taskName());
		assertEquals(TaskStatus.PENDING, response.taskStatus());
		assertFalse(response.isComplete());
		
		verify(taskRepository).save(any(Task.class));
	}
	
	@Test
	void createTaskShouldDefaultStatusToPending() {
		
		when(taskRepository.save(any(Task.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
		
		TaskResponse response = taskService.createTask(
				new CreateTaskRequest("Learn Spring Security")
		                                              );
		
		assertEquals(TaskStatus.PENDING, response.taskStatus());
		assertFalse(response.isComplete());
		
		verify(taskRepository).save(any(Task.class));
	}
	
	@Test
	void getAllTasksShouldReturnTasks() {
		
		when(taskRepository.findAll())
				.thenReturn(List.of(task));
		
		List<TaskResponse> responses = taskService.getAllTasks();
		
		assertEquals(1, responses.size());
		assertEquals("Learn Docker", responses.getFirst().taskName());
		
		verify(taskRepository).findAll();
	}
	
	@Test
	void getTaskByIdShouldReturnTask() {
		
		when(taskRepository.findById(1L))
				.thenReturn(Optional.of(task));
		
		TaskResponse response = taskService.getTaskById(1L);
		
		assertEquals("Learn Docker", response.taskName());
	}
	
	@Test
	void getTaskByIdShouldThrowWhenMissing() {
		
		when(taskRepository.findById(99L))
				.thenReturn(Optional.empty());
		
		assertThrows(
				TaskNotFoundException.class,
				() -> taskService.getTaskById(99L)
		            );
	}
	
	@Test
	void updateTaskShouldUpdateStatusAndCompletion() {
		
		when(taskRepository.findById(1L))
				.thenReturn(Optional.of(task));
		
		when(taskRepository.save(any(Task.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));
		
		TaskResponse response = taskService.updateTask(
				1L,
				new UpdateTaskRequest(
						"Learn Docker Compose",
						TaskStatus.COMPLETED
				)
		                                              );
		
		assertEquals(
				"Learn Docker Compose",
				response.taskName()
		            );
		
		assertEquals(
				TaskStatus.COMPLETED,
				response.taskStatus()
		            );
		
		assertTrue(response.isComplete());
	}
	
	@Test
	void deleteTaskShouldDeleteExistingTask() {
		
		when(taskRepository.findById(1L))
				.thenReturn(Optional.of(task));
		
		taskService.deleteTask(1L);
		
		verify(taskRepository).delete(task);
	}
	
	@Test
	void deleteTaskShouldThrowWhenMissing() {
		
		when(taskRepository.findById(99L))
				.thenReturn(Optional.empty());
		
		assertThrows(
				TaskNotFoundException.class,
				() -> taskService.deleteTask(99L)
		            );
		
		verify(taskRepository, never()).delete(any());
	}
}
