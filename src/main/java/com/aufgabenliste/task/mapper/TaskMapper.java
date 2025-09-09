package com.aufgabenliste.task.mapper;

import com.aufgabenliste.task.dto.TaskRequestDTO;
import com.aufgabenliste.task.dto.TaskResponseDTO;
import com.aufgabenliste.task.model.Task;

public class TaskMapper {
    public static Task toModel(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();

        task.setName(taskRequestDTO.getName());
        task.setDescription(taskRequestDTO.getDescription());
        task.setPriority(taskRequestDTO.getPriority());

        return task;
    }

    public static TaskResponseDTO toDto(Task task) {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setName(task.getName());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setPriority(task.getPriority());
        taskResponseDTO.setCreatedAt(task.getCreatedAt());
        taskResponseDTO.setUpdatedAt(task.getUpdatedAt());

        return taskResponseDTO;
    }
}
