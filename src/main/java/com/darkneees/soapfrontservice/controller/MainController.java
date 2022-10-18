package com.darkneees.soapfrontservice.controller;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.RoleServiceImpl;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import com.darkneees.soapfrontservice.service.windowservice.AddFormWindowServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @FXML
    private TableView tableData;
    @FXML
    private TableColumn usernameColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn surnameColumn;
    @FXML
    private TableColumn ageColumn;
    @FXML
    private TableColumn passwordColumn;
    @FXML
    private TextField fieldSearch;


    @FXML
    public void initialize(){
        log.info("Initialize");
        this.userService = new UserServiceImpl();
        this.roleService = new RoleServiceImpl();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    public void updateData() {
        Task<ObservableList<User>> task = new Task<>() {
            @Override
            protected ObservableList<User> call() {
                return FXCollections.observableList(userService.getAllUsers().join());
            }
        };
        tableData.itemsProperty().bind(task.valueProperty());
        executorService.submit(task);
    }

    public void addData() {
        AddFormWindowServiceImpl task = new AddFormWindowServiceImpl(800, 800, new Stage(), null);
        executorService.execute(task);
    }

    public void removeData() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                User user = (User) tableData.getSelectionModel().getSelectedItem();
                userService.deleteUserByUsername(user.getUsername())
                        .thenAccept((e) -> tableData.getItems().remove(user));
                return null;
            }
        };
        executorService.execute(task);
    }

    public void editData() {
        User tableUser = (User) tableData.getSelectionModel().getSelectedItem();
        User user = userService.getUserByUsername(tableUser.getUsername()).join();
        AddFormWindowServiceImpl task = new AddFormWindowServiceImpl(800, 800, new Stage(), user);
        executorService.execute(task);
    }
}