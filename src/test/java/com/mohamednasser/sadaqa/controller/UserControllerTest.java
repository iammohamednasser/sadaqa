package com.mohamednasser.sadaqa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamednasser.sadaqa.dto.UserDto;
import com.mohamednasser.sadaqa.security.AuthenticationService;
import com.mohamednasser.sadaqa.service.UserService;
import com.mohamednasser.sadaqa.testutils.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getUserInfoByIdTest() throws Exception {
        UserDto testUser = Users.FirstUser.USER_DTO;

        when(userService.findUserById(any())).thenReturn(testUser);
        mvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(testUser)));
    }

    @Test
    public void getUserInfoByHandleTest() throws Exception {
        UserDto testUser = Users.FirstUser.USER_DTO;

        when(userService.findUserByHandle(any())).thenReturn(testUser);
        mvc.perform(get("/users/?handle=mohamed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(testUser)));
    }

}
