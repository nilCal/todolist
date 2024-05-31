package com.mvp.todolist.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvp.todolist.controller.UserController;
import com.mvp.todolist.dto.UserDTO;
import com.mvp.todolist.entities.User;
import com.mvp.todolist.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserControllerTest() {
        objectMapper = new ObjectMapper();
    }

    //TODO Ajustar testes

    @Test
    public void testCreateUser() throws Exception {

        UserController userController = new UserController(userService, passwordEncoder, modelMapper);

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("123");

        User user = new User();
        user.setId(1L);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        User userCreate = modelMapper.map(userDTO, User.class);
        when(userService.createUser(any(User.class))).thenReturn(userCreate);
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

}
