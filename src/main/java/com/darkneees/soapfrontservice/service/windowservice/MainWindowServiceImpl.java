package com.darkneees.soapfrontservice.service.windowservice;

import com.darkneees.soapfrontservice.SoapApplication;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowServiceImpl implements WindowService {

    private int width;
    private int height;
    private Stage stage;

    public MainWindowServiceImpl(int width, int height, Stage stage) {
        this.width = width;
        this.height = height;

        FXMLLoader fxmlLoader = new FXMLLoader(SoapApplication.class.getResource("main.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setOnCloseRequest((e) -> {
            Platform.exit();
            System.exit(0);
        });
        stage.setTitle("Soap service");
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
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

}
