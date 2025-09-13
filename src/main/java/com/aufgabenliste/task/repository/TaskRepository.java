package com.aufgabenliste.task.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aufgabenliste.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatus(String status);

    List<Task> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Task> findByCreatedAtBetweenAndStatus(LocalDateTime start, LocalDateTime end, String status);
}
