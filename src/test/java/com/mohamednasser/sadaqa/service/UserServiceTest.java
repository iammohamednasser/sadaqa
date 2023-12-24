package com.mohamednasser.sadaqa.service;

import com.mohamednasser.sadaqa.dto.UserDto;
import com.mohamednasser.sadaqa.repository.RoleRepository;
import com.mohamednasser.sadaqa.repository.UserRepository;
import com.mohamednasser.sadaqa.testutils.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateUser() throws Exception {

        when(passwordEncoder.encode(Users.FirstUser.PASSWORD)).thenReturn(Users.FirstUser.PASSWORD);
        when(roleRepository.findByName(anyString())).thenReturn(Users.ROLE_USER);
        when(userRepository.save(any())).thenReturn(Users.FirstUser.USER_ENTITY);

        UserDto user = userService.createUser(Users.FirstUser.USER_REGISTRATION_DATA);

        Assertions.assertEquals(Users.FirstUser.USER_DTO, user);
    }

    @Test
    public void testFindByEmail_found() throws Exception {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(Users.FirstUser.USER_ENTITY));

        UserDto userData = userService.findUserByEmail(Users.FirstUser.EMAIL);

        Assertions.assertEquals(Users.FirstUser.USER_DTO, userData);
    }

    @Test
    public void testFindByEmail_notFound() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                Exception.class,
                () -> userService.findUserByEmail(Users.FirstUser.EMAIL), "No such user: " + Users.FirstUser.EMAIL);
    }
}
