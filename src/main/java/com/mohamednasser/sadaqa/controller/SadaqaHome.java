package com.mohamednasser.sadaqa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class SadaqaHome {

    private static final String ABOUT = "Sadaqa, a beautiful application";

    @GetMapping("/")
    public String home() {
        return "Welcome to Sadaqa";
    }

    @GetMapping("/about")
    public String about() {
        return ABOUT;
    }
}
