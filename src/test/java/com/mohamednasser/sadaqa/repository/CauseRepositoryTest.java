package com.mohamednasser.sadaqa.repository;

import com.mohamednasser.sadaqa.repository.CauseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase
class CauseRepositoryTest {

    @Autowired
    private CauseRepository causeRepository;


    @BeforeEach
    public void setup() {

    }

    @Test
    void findByIdTest() {

    }

}