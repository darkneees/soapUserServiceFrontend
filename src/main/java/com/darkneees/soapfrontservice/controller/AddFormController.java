package com.darkneees.soapfrontservice.controller;

import com.darkneees.soapfrontservice.entity.Role;
import com.darkneees.soapfrontservice.entity.Social;
import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.RoleServiceImpl;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;

import java.util.List;

public class AddFormController {
    private RoleServiceImpl roleService;
    private UserServiceImpl userService;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelName;
    @FXML
    private Label labelSurname;
    @FXML
    private Label labelAge;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField ageField;
    @FXML
    private VBox vboxSocial;
    @FXML
    private VBox vboxRoles;
    private List<Role> roles;

    private User editUser;

    @FXML
    public void initialize() {
        this.roleService = new RoleServiceImpl();
        this.userService = new UserServiceImpl();
        roles = roleService.getAllRoles().join();

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> labelUsername.setVisible(newValue.equals("")));
        nameField.textProperty().addListener((observable, oldValue, newValue) -> labelName.setVisible(newValue.equals("")));
        surnameField.textProperty().addListener((observable, oldValue, newValue) -> labelSurname.setVisible(newValue.equals("")));
        ageField.textProperty().addListener((observable, oldValue, newValue) -> labelAge.setVisible(!newValue.matches("\\d{1,2}")));
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> labelPassword.setVisible(newValue.equals("")));
    }

    public void submitForm() {

        if(!usernameField.getText().equals("") &&
                !nameField.getText().equals("") &&
                !surnameField.getText().equals("") &&
                !ageField.getText().equals("") &&
                !passwordField.getText().equals("")) {


            User user = new User();

            user.setUsername(usernameField.getText());
            user.setFirstName(nameField.getText());
            user.setSecondName(surnameField.getText());
            user.setAge(Integer.parseInt(ageField.getText()));
            user.setPassword(passwordField.getText());


            for (int i = 1; i < vboxRoles.getChildrenUnmodifiable().size(); i++) {
                HBox hBox = (HBox) vboxRoles.getChildrenUnmodifiable().get(i);

                ComboBox comboBox = (ComboBox) hBox.getChildrenUnmodifiable().get(0);
                String data = (String) comboBox.getSelectionModel().getSelectedItem();
                user.getRoleSet().add(new Role(roles.stream()
                        .filter((el) -> el.getNameRole().equals(data))
                        .findFirst().get().getId()));
            }

            for (int i = 1; i < vboxSocial.getChildrenUnmodifiable().size(); i++) {
                HBox hBox = (HBox) vboxSocial.getChildrenUnmodifiable().get(i);
                TextField fieldIdentifier = (TextField) hBox.getChildren().get(0);
                TextField fieldName = (TextField) hBox.getChildren().get(1);
                user.getSocialSet().add(new Social(fieldIdentifier.getText(), fieldName.getText()));
            }

            if(editUser == null) userService.addUser(user).join();
            else userService.editUser(user).join();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Exception input");
            alert.setContentText("Please fill in all fields!");
            alert.show();
        }

    }
    public HBox createRoleForm() {
        HBox hBox = new HBox();

        ComboBox comboBox = new ComboBox();
        comboBox.setPrefWidth(200);
        VBox.setMargin(comboBox, new Insets(20, 0, 0, 0));
        comboBox.setItems(FXCollections.observableList(roles.stream().map(Role::getNameRole).toList()));

        Button btn = new Button();
        btn.setText("X");
        btn.setOnMouseClicked(event -> vboxRoles.getChildren().remove(hBox));
        HBox.setMargin(btn, new Insets(0, 0, 0, 10));

        hBox.getChildren().addAll(comboBox, btn);

        VBox.setMargin(hBox, new Insets(10, 0, 0, 0));
        vboxRoles.getChildren().add(hBox);

        return hBox;
    }

    public HBox addSocialBox() {
        HBox hBox = new HBox();
        VBox.setMargin(hBox, new Insets(20, 10, 0, 0));

        TextField identifier = new TextField();
        identifier.setPromptText("Input identifier...");
        HBox.setHgrow(identifier, Priority.ALWAYS);
        HBox.setMargin(identifier, new Insets(0, 10, 0, 10));

        TextField nameSocial = new TextField();
        nameSocial.setPromptText("Input nameSocial...");
        HBox.setMargin(nameSocial, new Insets(0, 10, 0, 10));
        HBox.setHgrow(nameSocial, Priority.ALWAYS);

        Button btn = new Button();
        btn.setText("X");
        btn.setOnMouseClicked(event -> vboxSocial.getChildren().remove(hBox));

        hBox.getChildren().addAll(identifier, nameSocial, btn);
        vboxSocial.getChildren().add(hBox);

        return hBox;
    }

    public void setEditUser(User user) {
        if(user == null) return;
        this.editUser = user;
        usernameField.setText(editUser.getUsername());
        usernameField.setEditable(false);

        nameField.setText(editUser.getFirstName());
        surnameField.setText(editUser.getSecondName());
        passwordField.setText(editUser.getPassword());
        ageField.setText(String.valueOf(editUser.getAge()));

        editUser.getRoleSet().stream().forEach((el) -> {
            HBox hBox = createRoleForm();
            ComboBox comboBox = (ComboBox) hBox.getChildrenUnmodifiable().get(0);
            comboBox.setItems(FXCollections.observableList(roles.stream().map(Role::getNameRole).toList()));
            comboBox.getSelectionModel().select(el.getNameRole());
        });

        editUser.getSocialSet().stream().forEach((el) -> {
            HBox hBox = addSocialBox();
            TextField identifier = (TextField) hBox.getChildrenUnmodifiable().get(0);
            TextField nameSocial = (TextField) hBox.getChildrenUnmodifiable().get(1);

            identifier.setText(el.getNameSocial());
            nameSocial.setText(el.getNameSocial());
        });
    }
}
