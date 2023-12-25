package com.mohamednasser.sadaqa.service;

import com.mohamednasser.sadaqa.dto.UserDto;
import com.mohamednasser.sadaqa.dto.UserRegistrationData;
import com.mohamednasser.sadaqa.exception.UserNotFoundException;
import com.mohamednasser.sadaqa.model.Role;
import com.mohamednasser.sadaqa.model.User;
import com.mohamednasser.sadaqa.repository.RoleRepository;
import com.mohamednasser.sadaqa.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto createUser(UserRegistrationData data) {
        Role role = this.roleRepository.findByName("ROLE_USER");
        User user = User.builder()
                .handle(data.handle())
                .email(data.email())
                .role(role)
                .password(passwordEncoder.encode(data.password()))
                .build();
        User registredUser = userRepository.save(user);
        return new UserDto(registredUser.getEmail(), registredUser.getHandle());
    }

    public UserDto findUserByEmail(String email) throws Exception {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new Exception("No such user: " + email);
        }
        var user = optional.get();
        return new UserDto(user.getEmail(), user.getHandle());
    }

    public UserDto findUserById(Long id) throws Exception{
        Optional<User> optional = this.userRepository.findById(id);
        if (optional.isEmpty())
            throw new UserNotFoundException(id);
        var user = optional.get();
        return new UserDto(user.getEmail(), user.getHandle());
    }

    public UserDto findUserByHandle(String handle) throws Exception {

        Optional<User> optional = this.userRepository.findByHandle(handle);
        if (optional.isEmpty())
            throw UserNotFoundException.userNotFundByHandle(handle);
        var user = optional.get();
        return new UserDto(user.getEmail(), user.getHandle());
    }
}
