package com.mvp.todolist.test.controller;

import com.mvp.todolist.controller.TaskController;
import com.mvp.todolist.dto.TaskDTO;
import com.mvp.todolist.entities.Task;
import com.mvp.todolist.entities.User;
import com.mvp.todolist.service.TaskService;
import com.mvp.todolist.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    private TaskService taskService = mock(TaskService.class);
    private UserService userService = mock(UserService.class);
    private ModelMapper modelMapper = new ModelMapper();
    private TaskController taskController = new TaskController(taskService, userService, modelMapper);

    //TODO Ajustar testes

    @Test
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("New Task");
        taskDTO.setDescription("New Description");

        Task createdTask = new Task();
        createdTask.setId(1L);
        createdTask.setTitle("New Task");

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);
        ResponseEntity<Task> response = taskController.create(taskDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(createdTask, response.getBody());
    }

    @Test
    public void testFindTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");

        when(taskService.findTaskById(1L)).thenReturn(Optional.of(task));
        ResponseEntity<Optional<Task>> response = taskController.findTaskById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, Objects.requireNonNull(response.getBody()).orElse(null));
    }

    @Test
    public void testFindAllTasks() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmailcom");
        user.setName("Test");
        user.setPassword("123abc");
        //user.setTask(null);
        List<Task> tasks = List.of(
                new Task(1L, "Task 1", "Description", LocalDateTime.now(), LocalDateTime.now(), 1L, "Pending", user),
                new Task(2L, "Task 2", "Description", LocalDateTime.now(), LocalDateTime.now(), 1L, "Done", user));

        when(taskService.findAllTasks()).thenReturn(Optional.of(tasks));
        ResponseEntity<Optional<List<Task>>> response = taskController.findAllTasks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertFalse(response.getBody().get().isEmpty());
    }

    @Test
    public void testfindTaskDetailsById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Task");
        task.setCreationDate(LocalDateTime.now());
        task.setUpdateDate(LocalDateTime.now());
        //task.setUserCode(1L);
        task.setStatus("Done");
        task.setUser(null);

        when(taskService.findTaskById(1L)).thenReturn(Optional.of(task));
        ResponseEntity<TaskDTO> response = taskController.findTaskDetailsById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
