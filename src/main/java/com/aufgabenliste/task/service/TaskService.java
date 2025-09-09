package com.aufgabenliste.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aufgabenliste.task.dto.TaskPatchRequestDTO;
import com.aufgabenliste.task.dto.TaskRequestDTO;
import com.aufgabenliste.task.dto.TaskResponseDTO;
import com.aufgabenliste.task.exceptions.TaskNotFoundException;
import com.aufgabenliste.task.mapper.TaskMapper;
import com.aufgabenliste.task.model.Task;
import com.aufgabenliste.task.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task newTask = taskRepository.save(TaskMapper.toModel(taskRequestDTO));
        return TaskMapper.toDto(newTask);
    }

    public List<TaskResponseDTO> readAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        List<TaskResponseDTO> taskResponseDTOs = new ArrayList<>();

        for (Task task : tasks) {
            taskResponseDTOs.add(TaskMapper.toDto(task));
        }

        return taskResponseDTOs;
    }

    public TaskResponseDTO readTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        return TaskMapper.toDto(task);
    }

    public TaskResponseDTO patchTask(UUID id, TaskPatchRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            task.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            task.setDescription(dto.getDescription());
        }

        if (dto.getPriority() != null && !dto.getPriority().isBlank()) {
            task.setPriority(dto.getPriority());
        }

        return TaskMapper.toDto(taskRepository.save(task));
    }

    public void deleteTask(UUID id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        taskRepository.deleteById(id);
    }
}
