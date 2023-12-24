package com.mohamednasser.sadaqa.controller;

import com.mohamednasser.sadaqa.dto.UserDto;
import com.mohamednasser.sadaqa.dto.UserLoginData;
import com.mohamednasser.sadaqa.dto.UserRegistrationData;
import com.mohamednasser.sadaqa.security.AuthenticationService;
import com.mohamednasser.sadaqa.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
 * TODO - add Exceptions Advice for all controllers
 */
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {
    UserService userService;

    AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody UserRegistrationData userRegistrationData) throws Exception {
        UserDto user = this.userService.createUser(userRegistrationData);
        return user;
    }

    /*
     * TODO - check if user exist in the database and return a UserDto in the body
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserLoginData credentials) {
        HttpHeaders headers = new HttpHeaders();
        try {
            String jwtToken = authenticationService.authenticate(credentials);
            headers.add("Authorization", jwtToken);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
