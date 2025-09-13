package com.aufgabenliste.task.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aufgabenliste.task.dto.TaskPatchRequestDTO;
import com.aufgabenliste.task.dto.TaskRequestDTO;
import com.aufgabenliste.task.dto.TaskResponseDTO;
import com.aufgabenliste.task.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "CRUD operations for managing tasks")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@Operation(summary = "Create a new task", description = "Creates a new task with name, description, and priority", responses = {
			@ApiResponse(responseCode = "201", description = "Task created", content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
	})
	@PostMapping
	public ResponseEntity<TaskResponseDTO> createTask(
			@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
		TaskResponseDTO taskResponseDTO = taskService.createTask(taskRequestDTO);
		return ResponseEntity
				.created(URI.create("/api/v1/tasks/" + taskResponseDTO.getId()))
				.body(taskResponseDTO);
	}

	@Operation(summary = "Get all tasks", description = "Fetches all tasks in the system", responses = {
			@ApiResponse(responseCode = "200", description = "List of tasks returned", content = @Content(schema = @Schema(implementation = TaskResponseDTO.class)))
	})
	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
		List<TaskResponseDTO> taskResponseDTOs = taskService.readAllTasks();
		return ResponseEntity.ok(taskResponseDTOs);
	}

	@Operation(summary = "Get task by ID", description = "Fetches a task by its unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "Task found", content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
	})
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> getTaskById(
			@Parameter(description = "UUID of the task to retrieve") @PathVariable("id") UUID id) {
		TaskResponseDTO taskResponseDTO = taskService.readTaskById(id);
		return ResponseEntity.ok(taskResponseDTO);
	}

	@Operation(summary = "Filter tasks", description = "Fetches tasks based on optional filters: createdAt date and/or status", responses = {
			@ApiResponse(responseCode = "200", description = "List of filtered tasks returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponseDTO.class)))
	})
	@GetMapping("/filter")
	public ResponseEntity<List<TaskResponseDTO>> getTasksByFilter(
			@Parameter(description = "Filter tasks by creation date (yyyy-MM-dd). Example: 2025-09-13", example = "2025-09-13") @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

			@Parameter(description = "Filter tasks by status (e.g., Pending or Completed)", example = "Pending") @RequestParam(value = "status", required = false) String status) {

		List<TaskResponseDTO> taskResponseDTOs = taskService.readTaskByFilter(date, status);
		return ResponseEntity.ok(taskResponseDTOs);
	}

	@Operation(summary = "Partially update a task", description = "Updates one or more fields of a task (name, description, priority). Fields not provided remain unchanged.", responses = {
			@ApiResponse(responseCode = "200", description = "Task updated", content = @Content(schema = @Schema(implementation = TaskResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
	})
	@PatchMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> patchTask(
			@Parameter(description = "UUID of the task to update") @PathVariable("id") UUID id,
			@Valid @RequestBody TaskPatchRequestDTO taskPatchRequestDTO) {

		TaskResponseDTO taskResponseDTO = taskService.patchTask(id, taskPatchRequestDTO);
		return ResponseEntity.ok(taskResponseDTO);
	}

	@Operation(summary = "Delete a task", description = "Deletes a task by its unique ID", responses = {
			@ApiResponse(responseCode = "204", description = "Task deleted", content = @Content),
			@ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(
			@Parameter(description = "UUID of the task to delete") @PathVariable("id") UUID id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
