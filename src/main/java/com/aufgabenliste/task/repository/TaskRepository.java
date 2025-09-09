package com.aufgabenliste.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aufgabenliste.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}
