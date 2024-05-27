package com.mvp.todolist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvp.todolist.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Optional<Task> findTaskDetailsById(Long id);

}
