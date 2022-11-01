package com.darkneees.soapfrontservice.task;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import javafx.concurrent.Task;

public class GetUserByUsernameTask extends Task<User> {

    UserServiceImpl userService;
    String username;

    public GetUserByUsernameTask(String username){
        userService = UserServiceImpl.getInstance();
        this.username = username;
    }


    @Override
    protected User call() throws Exception {
        return userService.getUserByUsername(username).join();
    }
}
