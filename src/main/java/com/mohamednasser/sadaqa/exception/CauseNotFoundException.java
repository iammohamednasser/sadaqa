package com.mohamednasser.sadaqa.exception;

public class CauseNotFoundException extends Exception {

    public CauseNotFoundException(long postId) {
        super("Cause not found with ID: " + postId);
    }
}
