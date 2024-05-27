package com.mvp.todolist.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvp.todolist.dto.TaskDTO;
import com.mvp.todolist.entities.Task;
import com.mvp.todolist.repository.TaskRepository;

@Service
public class TaskService {

	private TaskRepository taskRepository;
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
		List<Task> tasks = new ArrayList<Task>();
		taskRepository.findAll().forEach(tasks::add);
		return Optional.of(tasks);
	}

	public TaskDTO findTaskDetailsById(Long id) {
		 Optional<Task> task = taskRepository.findTaskDetailsById(id);
		 return modelMapper.map(task,TaskDTO.class);
	}

	public Optional<Task> update(Long id, Task task) {
		Optional<Task> taskToUpdate = taskRepository.findById(id);
		taskToUpdate.ifPresent(taskWithNewValues -> {
			taskWithNewValues.setTitle(task.getTitle());
			taskWithNewValues.setDescription(task.getDescription());
			taskWithNewValues.setCreationDate(taskToUpdate.get().getCreationDate());
			taskWithNewValues.setUpdateDate(LocalDateTime.now());
			taskWithNewValues.setUserCode(task.getUserCode());
			taskWithNewValues.setStatus(task.getStatus());
			taskRepository.save(taskWithNewValues);
		});
		return Optional.of(taskToUpdate.orElse(null));
	}

	public void delete(Long id) {
		taskRepository.deleteById(id);
	}

}
