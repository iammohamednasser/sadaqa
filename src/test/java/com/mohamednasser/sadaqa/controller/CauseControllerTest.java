package com.mohamednasser.sadaqa.controller;

import com.mohamednasser.sadaqa.service.CauseService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(SpringExtension.class)
@WebMvcTest(CauseController.class)
class CauseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CauseService causeService;


    void getCauseTest() {


    }

}