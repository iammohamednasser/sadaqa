package com.mohamednasser.sadaqa.controller;

import com.mohamednasser.sadaqa.dto.UserDto;
import com.mohamednasser.sadaqa.dto.UserLoginData;
import com.mohamednasser.sadaqa.dto.UserRegistrationData;
import com.mohamednasser.sadaqa.security.AuthenticationService;
import com.mohamednasser.sadaqa.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}")
    public UserDto getUserInfoById(@PathVariable Long id) throws Exception {
        return this.userService.findUserById(id);
    }

    @GetMapping("/")
    public UserDto getUserInfoByHandle(@RequestParam("handle") String handle) throws Exception {
        return this.userService.findUserByHandle(handle);
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody UserRegistrationData userRegistrationData) throws Exception {
        return this.userService.createUser(userRegistrationData);
    }

    /*
     * TODO - check if user exist in the database and return a UserDto in the body
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginData credentials) {
        HttpHeaders headers = new HttpHeaders();
        String jwtToken = authenticationService.authenticate(credentials);
        headers.add("Authorization", jwtToken);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body("successful login");
    }
}
