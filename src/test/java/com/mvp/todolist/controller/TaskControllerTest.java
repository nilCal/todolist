package com.mvp.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvp.todolist.dto.TaskDTO;
import com.mvp.todolist.entities.Task;
import com.mvp.todolist.entities.User;
import com.mvp.todolist.service.TaskService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAllTasks();
    }

    Task task;
    TaskDTO taskDTO;
    User user;
    Optional<Task> optionalTask;

    private static final Long ID = 1L;

    private static final String TITLE = "Test Task";
    private static final String DESCRIPTION = "Test Task Description";
    private static final Long USERCODE = 1L;
    private static final String STATUS = "Pending";

    private static final String NAME = "Test";
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "123";

    @Test
    public void shouldInstantiateMapper() {
        MatcherAssert.assertThat(objectMapper, is(notNullValue()));
    }

    @Test
    public void whenCreateTaskReturnCreated() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(post("/api/task/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title", is("Test Task")));
    }

    @Test
    public void whenCreateTaskReturnNotFound() throws Exception {

        String token = generetedLoginToken();
        taskDTO.setUserCode(55L);
        mockMvc.perform(post("/api/task/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateTaskReturnBadRequest() throws Exception {

        String token = generetedLoginToken();
        taskDTO.setTitle(null);
        mockMvc.perform(post("/api/task/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenIFindTaskByIdReturnOk() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(get("/api/task/findById/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenIFindTaskByIdReturnNotFound() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(get("/api/task/findById/78")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenIFindAllTasksReturnOk() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(get("/api/task/findAll")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenIFindDetailsByIdReturnOk() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(get("/api/task/details/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenIFindDetailsByIdReturnNotFound() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(get("/api/task/details/100")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenIUpdateTaskByIdReturnOk() throws Exception {

        String token = generetedLoginToken();
        taskDTO.setStatus("Done");
        mockMvc.perform(put("/api/task/update/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("Done")));
    }

    @Test
    public void whenIUpdateTaskByIdReturnNotFound() throws Exception {

        String token = generetedLoginToken();
        taskDTO.setStatus("Done");
        mockMvc.perform(put("/api/task/update/250")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenIDeleteTaskReturnOk() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(delete("/api/task/delete/2")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenIDeleteTaskReturnNotFound() throws Exception {

        String token = generetedLoginToken();
        mockMvc.perform(delete("/api/task/delete/250")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    private void startAllTasks() {
        user = new User(ID, NAME, EMAIL, PASSWORD, null);
        task = new Task(ID, TITLE, DESCRIPTION, LocalDateTime.now(), LocalDateTime.now(), USERCODE, STATUS, user);
        taskDTO = new TaskDTO(TITLE, DESCRIPTION, USERCODE, STATUS);
        optionalTask = Optional.of(new Task(ID, TITLE, DESCRIPTION, LocalDateTime.now(), LocalDateTime.now(), USERCODE, STATUS, user));
    }

    private String generetedLoginToken() throws Exception {

        String body = "{\n" +
                "    \"email\":\"admin@gmail.com\",\n" +
                "    \"password\":\"123\"\n" +
                "}";

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

}
