package com.mohamednasser.sadaqa.repository;

import com.mohamednasser.sadaqa.model.Role;
import com.mohamednasser.sadaqa.model.User;
import com.mohamednasser.sadaqa.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder().
                handle("mohamed")
                .email("mohamed@gmail.com")
                .password("9812")
                .role(new Role(1, null))
                .build();

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        this.user = null;
        this.userRepository.deleteAll();
    }

    @Test
    public void testFindByEmail_found() {
        Optional<User> optional = userRepository.findByEmail("mohamed@gmail.com");

        Assertions.assertTrue(optional.isPresent());

        User actual = optional.get();

        Assertions.assertEquals(actual.getEmail(), user.getEmail());
        Assertions.assertEquals(actual.getHandle(), user.getHandle());
        Assertions.assertEquals(actual.getPassword(), user.getPassword());
        Assertions.assertEquals(actual.getRole(), user.getRole());

    }

    @Test
    public void testFindByEmail_notFound() {
        Optional<User> optional = userRepository.findByEmail("mohammed@gmail.com");
        Assertions.assertTrue(optional.isEmpty());
    }

}
