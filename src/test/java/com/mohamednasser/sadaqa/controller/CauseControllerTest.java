package com.mohamednasser.sadaqa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamednasser.sadaqa.dto.CauseOut;
import com.mohamednasser.sadaqa.security.AuthenticationService;
import com.mohamednasser.sadaqa.security.JwtService;
import com.mohamednasser.sadaqa.service.CauseService;
import com.mohamednasser.sadaqa.testutils.Causes;
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
@WebMvcTest(CauseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CauseControllerTest {

    @MockBean
    private CauseService causeService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getCauseTest() throws Exception {
        CauseOut testCause = Causes.FirstCause.CAUSE_OUT;
        when(causeService.getCause(any())).thenReturn(testCause);

        mvc.perform(get("/causes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(testCause)));
    }
}
