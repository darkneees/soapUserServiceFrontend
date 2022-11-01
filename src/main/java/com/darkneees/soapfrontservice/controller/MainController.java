package com.darkneees.soapfrontservice.controller;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.log.StaticOutputStreamAppender;
import com.darkneees.soapfrontservice.log.TextAreaLogger;
import com.darkneees.soapfrontservice.service.windowservice.AddFormWindowServiceImpl;
import com.darkneees.soapfrontservice.service.windowservice.InfoUserWindowServiceImpl;
import com.darkneees.soapfrontservice.task.DeleteUserTask;
import com.darkneees.soapfrontservice.task.GetUserByUsernameTask;
import com.darkneees.soapfrontservice.task.UpdateTableTask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private Timeline loading;
    @FXML
    private HBox boxCircles;
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
        initAnimation();

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
                    infoUserWindowService.setOnRunning((e) -> startAnimation());
                    infoUserWindowService.setOnSucceeded((e) -> stopAnimation());
                    executorService.execute(infoUserWindowService);
                }
            });
            return row;
        });
    }


    public void updateData() {
        Task<ObservableList<User>> task = new UpdateTableTask();
        tableData.itemsProperty().bind(task.valueProperty());
        task.setOnRunning((e) -> startAnimation());
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
            stopAnimation();
        });
        executorService.submit(task);
    }

    public void addData() {
        AddFormWindowServiceImpl task = new AddFormWindowServiceImpl(800, 800, new Stage(), null);
        task.setOnRunning((e) -> startAnimation());
        task.setOnSucceeded((e) -> stopAnimation());
        executorService.execute(task);
    }

    public void removeData() {
        User user = (User) tableData.getSelectionModel().getSelectedItem();
        Task<ObservableList<User>> task = new DeleteUserTask(user);
        task.setOnRunning((e) -> startAnimation());
        task.setOnSucceeded((e) -> {
            log.info("Success delete user");
            stopAnimation();
        });
        tableData.itemsProperty().bind(task.valueProperty());
        executorService.execute(task);

    }

    public void editData() {
        User tableUser = (User) tableData.getSelectionModel().getSelectedItem();
        GetUserByUsernameTask task = new GetUserByUsernameTask(tableUser.getUsername());
        task.setOnRunning((e) -> startAnimation());
        task.setOnSucceeded((e) -> {
            AddFormWindowServiceImpl taskOpen = new AddFormWindowServiceImpl(800, 800, new Stage(), task.getValue());
            executorService.execute(taskOpen);
            stopAnimation();
        });
        executorService.submit(task);
    }

    public void clearLogArea() {
        logTextArea.clear();
    }

    private void initAnimation() {
        loading = new Timeline();
        List<Node> nodes = boxCircles.getChildren();
        loading.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                        new KeyValue(nodes.get(0).translateYProperty(), 0)
                ),
                new KeyFrame(Duration.millis(125),
                        new KeyValue(nodes.get(0).translateYProperty(), -25)
                ),
                new KeyFrame(Duration.millis(250),
                        new KeyValue(nodes.get(0).translateYProperty(), 0)
                ));

        loading.getKeyFrames().addAll(new KeyFrame(Duration.millis(250),
                        new KeyValue(nodes.get(1).translateYProperty(), 0)
                ),
                new KeyFrame(Duration.millis(375),
                        new KeyValue(nodes.get(1).translateYProperty(), -25)
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(nodes.get(1).translateYProperty(), 0)
                ));

        loading.getKeyFrames().addAll(new KeyFrame(Duration.millis(500),
                        new KeyValue(nodes.get(2).translateYProperty(), 0)
                ),
                new KeyFrame(Duration.millis(625),
                        new KeyValue(nodes.get(2).translateYProperty(), -25)
                ),
                new KeyFrame(Duration.millis(750),
                        new KeyValue(nodes.get(2).translateYProperty(), 0)
                ));
        loading.setCycleCount(Timeline.INDEFINITE);
    }

    private void startAnimation(){
        List<Node> nodes = boxCircles.getChildren();
        nodes.stream().forEach((n) -> {
            Circle c = (Circle) n;
            c.setFill(Color.YELLOW);
        });
        loading.play();
    }

    private void stopAnimation(){
        List<Node> nodes = boxCircles.getChildren();
        nodes.stream().forEach((n) -> {
            Circle c = (Circle) n;
            c.setFill(Color.GREEN);
        });
        loading.jumpTo(Duration.ZERO);
        loading.stop();
    }
}