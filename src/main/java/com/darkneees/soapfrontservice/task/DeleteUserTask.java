package com.darkneees.soapfrontservice.task;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class DeleteUserTask extends Task<ObservableList<User>> {
    private User user;
    private final UserServiceImpl userService;

    public DeleteUserTask(User user) {
        this.user = user;
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    protected ObservableList<User> call() {
        return userService.deleteUserByUsername(user.getUsername())
                .thenApply((e) -> FXCollections.observableList(userService.getAllUsers().join())).join();
    }
}
