package com.darkneees.soapfrontservice.service.windowservice;

import com.darkneees.soapfrontservice.SoapApplication;
import com.darkneees.soapfrontservice.controller.AddFormController;
import com.darkneees.soapfrontservice.entity.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AddFormWindowServiceImpl extends Task<Void> implements WindowService {

    private int width;
    private int height;
    private Stage stage;
    private User user;

    public AddFormWindowServiceImpl(int width, int height, Stage stage, User user) {
        this.width = width;
        this.height = height;
        this.stage = stage;
        this.user = user;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    protected Void call() {
        CompletableFuture.runAsync(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(SoapApplication.class.getResource("addForm.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load(), width, height);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AddFormController controller = fxmlLoader.getController();
            controller.setEditUser(user);
            controller.setStage(stage);
            stage.setTitle("Add/Edit service");
            stage.setScene(scene);
            stage.show();
        }, Platform::runLater).join();
        return null;
    }
}
