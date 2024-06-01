package com.mvp.todolist.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mvp.todolist.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mvp.todolist.dto.TaskDTO;
import com.mvp.todolist.entities.Task;
import com.mvp.todolist.repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final ModelMapper modelMapper;
	private final UserService userService;
	
	public TaskService(TaskRepository taskRepository, ModelMapper modelMapper, UserService userService) {
		this.taskRepository = taskRepository;
		this.modelMapper = modelMapper;
		this.userService = userService;
	}

	public Task createTask(TaskDTO taskDTO) {
		Optional<User> user = userService.findById(taskDTO.getUserCode());
		Task task = modelMapper.map(taskDTO, Task.class);
		if (user.isPresent()) {
			task.setUser(user.orElse(null));
			task.setCreationDate(LocalDateTime.now());
			task.setUpdateDate(LocalDateTime.now());
			return taskRepository.save(task);
		}
		return null;
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

	public Optional<Task> update(Long id, TaskDTO taskDTO) {
		Optional<Task> taskToUpdate = taskRepository.findById(id);
		if(taskToUpdate.isPresent()) {
			Task task = modelMapper.map(taskDTO, Task.class);
			taskToUpdate.get().setTitle(task.getTitle());
			taskToUpdate.get().setDescription(task.getDescription());
			taskToUpdate.get().setUpdateDate(LocalDateTime.now());
			taskToUpdate.get().setStatus(task.getStatus());
			taskRepository.save(taskToUpdate.get());
			return Optional.of(taskToUpdate.orElse(null));
		}
		return Optional.empty();
	}

	public boolean delete(Long id) {
		Optional<Task> taskToDelete = taskRepository.findById(id);
		if(taskToDelete.isPresent()) {
			taskRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
