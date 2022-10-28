package com.darkneees.soapfrontservice.task;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class UpdateTableTask extends Task<ObservableList<User>> {


    private final UserServiceImpl userService;

    public UpdateTableTask() {
        this.userService = UserServiceImpl.getInstance();
    }

    @Override
    protected ObservableList<User> call() {
        return FXCollections.observableList(userService.getAllUsers().join());
    }
}
