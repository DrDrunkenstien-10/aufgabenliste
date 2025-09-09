package com.aufgabenliste.task.dto;

import jakarta.validation.constraints.Size;

public class TaskPatchRequestDTO {
    @Size(min = 1, max = 100, message = "Task name must be between 1 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(min = 1, max = 10, message = "Priority must be between 1 and 10 characters")
    private String priority;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
