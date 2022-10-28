package com.darkneees.soapfrontservice.controller;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.log.StaticOutputStreamAppender;
import com.darkneees.soapfrontservice.log.TextAreaLogger;
import com.darkneees.soapfrontservice.service.RoleServiceImpl;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import com.darkneees.soapfrontservice.service.windowservice.AddFormWindowServiceImpl;
import com.darkneees.soapfrontservice.service.windowservice.InfoUserWindowServiceImpl;
import com.darkneees.soapfrontservice.task.DeleteUserTask;
import com.darkneees.soapfrontservice.task.UpdateTableTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
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
    private TextArea logTextArea;

    @FXML
    public void initialize(){
        log.info("Initialize");

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        StaticOutputStreamAppender.setDelegatingOutputStream(new TextAreaLogger(logTextArea));
        updateData();

        tableData.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())) {
                    User user = row.getItem();
                    Task<Void> infoUserWindowService = new InfoUserWindowServiceImpl(800, 500, new Stage(), user);
                    executorService.execute(infoUserWindowService);
                }
            });
            return row;
        });
    }


    public void updateData() {
        Task<ObservableList<User>> task = new UpdateTableTask();
        tableData.itemsProperty().bind(task.valueProperty());
        task.setOnSucceeded((e) -> {
            tableData.itemsProperty().unbind();
            log.info("Table successful updated");

            FilteredList<User> filteredData = new FilteredList<>(tableData.getItems(), p -> true);

            fieldSearch.textProperty().addListener((observable, oldValue, newValue) ->
                    filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (person.getSecondName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (String.valueOf(person.getAge()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (person.getPassword().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            }));

            SortedList<User> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableData.comparatorProperty());
            tableData.setItems(sortedData);

        });
        executorService.submit(task);
    }

    public void addData() {
        AddFormWindowServiceImpl task = new AddFormWindowServiceImpl(800, 800, new Stage(), null);
        executorService.execute(task);
    }

    public void removeData() {
        User user = (User) tableData.getSelectionModel().getSelectedItem();
        Task<ObservableList<User>> task = new DeleteUserTask(user);
        tableData.itemsProperty().bind(task.valueProperty());
        executorService.execute(task);
        task.setOnSucceeded((e) -> log.info("Success delete user"));
    }

    public void editData() {
        User tableUser = (User) tableData.getSelectionModel().getSelectedItem();
//        User user = userService.getUserByUsername(tableUser.getUsername()).join();
//        AddFormWindowServiceImpl task = new AddFormWindowServiceImpl(800, 800, new Stage(), user);
//        executorService.execute(task);
    }
}