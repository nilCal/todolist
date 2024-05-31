package com.mvp.todolist.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mvp.todolist.dto.TaskDTO;
import com.mvp.todolist.entities.Task;
import com.mvp.todolist.repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final ModelMapper modelMapper;
	
	public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
		this.taskRepository = taskRepository;
		this.modelMapper = modelMapper;
	}

	public Task createTask(Task task) {
		task.setCreationDate(LocalDateTime.now());
        task.setUpdateDate(LocalDateTime.now());
		return taskRepository.save(task);
	}

	public Optional<Task> findTaskById(Long taskId) {
		return taskRepository.findById(taskId);
	}

	public Optional<List<Task>> findAllTasks() {
		List<Task> tasks = new ArrayList<>();
		taskRepository.findAll().forEach(tasks::add);
		return Optional.of(tasks);
	}

	public TaskDTO findTaskDetailsById(Long id) {
		 Optional<Task> task = taskRepository.findTaskDetailsById(id);
		 return modelMapper.map(task,TaskDTO.class);
	}

	public Optional<Task> update(Task task, Optional<Task> taskToUpdate) {
		taskToUpdate.get().setTitle(task.getTitle());
		taskToUpdate.get().setDescription(task.getDescription());
		taskToUpdate.get().setUpdateDate(LocalDateTime.now());
		taskToUpdate.get().setStatus(task.getStatus());
		taskRepository.save(taskToUpdate.get());
		return Optional.of(taskToUpdate.orElse(null));
	}

	public void delete(Long id) {
		taskRepository.deleteById(id);
	}

}
