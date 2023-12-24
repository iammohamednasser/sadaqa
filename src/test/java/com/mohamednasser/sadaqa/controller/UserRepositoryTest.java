package com.mohamednasser.sadaqa.controller;

import com.mohamednasser.sadaqa.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class UserRepositoryTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

}
