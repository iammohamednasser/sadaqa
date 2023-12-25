package com.mohamednasser.sadaqa.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(long id) {
        super("User not found. id:" + id);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException userNotFundByHandle(String handle) {
        return new UserNotFoundException("User not found. handle=" + handle);
    }

    public static UserNotFoundException userNotFundByEmail(String email) {
        return new UserNotFoundException("User not found. email=" + email);
    }
}
