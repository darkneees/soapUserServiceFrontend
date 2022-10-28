package com.darkneees.soapfrontservice.controller;

import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InfoUserController {

    private UserServiceImpl userService;

    @FXML
    private TableColumn rolesColumn;
    @FXML
    private TableColumn identifierColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TableView rolesTable;
    @FXML
    private TableView socialsTable;

    @FXML
    public void initialize() {
        this.userService = UserServiceImpl.getInstance();
    }


    public void setUser(User user) {
        if(user != null) {
            user = userService.getUserByUsername(user.getUsername()).join();

            usernameLabel.setText(user.getUsername());
            nameLabel.setText(user.getFirstName());
            surnameLabel.setText(user.getSecondName());
            ageLabel.setText(String.valueOf(user.getAge()));
            passwordLabel.setText(user.getPassword());

            rolesColumn.setCellValueFactory(new PropertyValueFactory<>("nameRole"));
            identifierColumn.setCellValueFactory(new PropertyValueFactory<>("identifierSocial"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameSocial"));

            rolesTable.getItems().addAll(user.getRoleSet());
            socialsTable.getItems().addAll(user.getSocialSet());

        }
    }



}
