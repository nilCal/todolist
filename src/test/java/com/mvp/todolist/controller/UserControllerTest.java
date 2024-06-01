package com.mvp.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvp.todolist.dto.UserDTO;
import com.mvp.todolist.entities.User;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAllUsers();
    }

    User user;
    UserDTO userDTO;

    private static final Long ID = 1L;
    private static final String NAME = "Test";
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "123";

    @Test
    public void shouldInstantiateMapper() {
        MatcherAssert.assertThat(objectMapper, is(notNullValue()));
    }

    @Test
    public void whenCreateUserReturnOk() throws Exception {

        mockMvc.perform(post("/api/user/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Test")))
                .andExpect(jsonPath("id", is(3)));// Deve retornar 3 pois já temos dois user cadastrados!
    }

    @Test
    public void whenCreateUserWithoutEmailReturnBadRequest() throws Exception {

        userDTO.setEmail(null);
        mockMvc.perform(post("/api/user/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    private void startAllUsers() {
        user = new User(ID, NAME, EMAIL, PASSWORD, null);
        userDTO = new UserDTO(NAME, EMAIL, PASSWORD);
    }

}
