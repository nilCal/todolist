package com.mvp.todolist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.mvp.todolist.entities.User;
import com.mvp.todolist.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mvp.todolist.dto.TaskDTO;
import com.mvp.todolist.entities.Task;
import com.mvp.todolist.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	private final TaskService taskService;
	private final UserService userService;
	private final ModelMapper modelMapper;

	public TaskController(TaskService taskService, UserService userService, ModelMapper modelMapper) {
		this.taskService = taskService;
		this.modelMapper = modelMapper;
		this.userService = userService;
	}

	@PostMapping(value = "/create")
	public ResponseEntity<Task> create(@RequestBody @Valid TaskDTO taskDto) {
		try {
			Optional<User> user = userService.findById(taskDto.getUserCode());
			Task task = modelMapper.map(taskDto, Task.class);
			if (user.isPresent()) {
				task.setUser(user.orElse(null));
				Task createdTask = taskService.createTask(task);
				return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Optional<Task>> findTaskById(@PathVariable Long id) {
		Optional<Task> task = taskService.findTaskById(id);
		if(task.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(task);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping(value = "/findAll")
	public ResponseEntity<Optional<List<Task>>> findAllTasks() {
		Optional<List<Task>> allTasks = taskService.findAllTasks();
		if(allTasks.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(allTasks);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping(value = "/details/{id}")
	public ResponseEntity<TaskDTO> findTaskDetailsById(@PathVariable Long id) {
		TaskDTO task = taskService.findTaskDetailsById(id);
		if (task != null) {
			return ResponseEntity.status(HttpStatus.OK).body(task);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Optional<Task>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDto) {
		Optional<Task> taskToUpdate = taskService.findTaskById(id);
		if(taskToUpdate.isPresent()) {
			Task task = modelMapper.map(taskDto, Task.class);
			Optional<Task> taskUpdated = taskService.update(task, taskToUpdate);
			return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		Optional<Task> taskToDelete = taskService.findTaskById(id);
		if(taskToDelete.isPresent()) {
			taskService.delete(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
