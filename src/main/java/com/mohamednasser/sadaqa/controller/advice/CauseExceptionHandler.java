package com.mohamednasser.sadaqa.controller.advice;


import com.mohamednasser.sadaqa.exception.CauseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CauseExceptionHandler {

    @ExceptionHandler(CauseNotFoundException.class)
    public ResponseEntity<String> CauseNotFoundExceptionHandler(CauseNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception.getMessage());
    }
}
