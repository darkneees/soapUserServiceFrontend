package com.darkneees.soapfrontservice.task;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import javafx.concurrent.Task;

public class EditUserTask extends Task<Void> {

    private User user;

    private final UserServiceImpl userService;

    public EditUserTask(User user) {
        this.user = user;
        this.userService = UserServiceImpl.getInstance();
    }


    @Override
    protected Void call() throws Exception {
        userService.editUser(user);

        return null;
    }
}
