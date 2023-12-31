package com.mohamednasser.sadaqa.testutils;

import com.mohamednasser.sadaqa.dto.UserDto;
import com.mohamednasser.sadaqa.dto.UserLoginData;
import com.mohamednasser.sadaqa.dto.UserRegistrationData;
import com.mohamednasser.sadaqa.model.Role;
import com.mohamednasser.sadaqa.model.User;

public class Users {

    public static final Role ROLE_USER = new Role(1, "ROLE_USER");


    public static class FirstUser {

        public static final String EMAIL = "mohamed@gmail.com";

        public static final String PASSWORD = "9812";

        public static final String HANDLE = "mohamed";

        public static final User USER_ENTITY;

        public static final UserRegistrationData USER_REGISTRATION_DATA;

        public static final UserDto USER_DTO;

        public static final UserLoginData USER_LOGIN_DATA;

        static {

            USER_ENTITY = User.builder()
                    .id(1L)
                    .handle(HANDLE)
                    .email(EMAIL)
                    .password(PASSWORD)
                    .role(ROLE_USER)
                    .build();

            USER_REGISTRATION_DATA = new UserRegistrationData(EMAIL, HANDLE, PASSWORD);

            USER_DTO = new UserDto(EMAIL, HANDLE);

            USER_LOGIN_DATA = new UserLoginData(EMAIL, PASSWORD);

        }
    }
}
