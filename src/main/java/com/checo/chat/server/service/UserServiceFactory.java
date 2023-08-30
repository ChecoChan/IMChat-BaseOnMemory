package com.checo.chat.server.service;

import lombok.Getter;

public abstract class UserServiceFactory {

    @Getter
    private static UserService userService = new UserServiceMemoryImpl();
}
