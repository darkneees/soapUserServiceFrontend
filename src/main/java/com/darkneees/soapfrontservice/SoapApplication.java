package com.darkneees.soapfrontservice;

import com.darkneees.soapfrontservice.service.windowservice.MainWindowServiceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class SoapApplication extends Application {

    @Override
    public void start(Stage stage) {
        new MainWindowServiceImpl(1440, 900, stage);
    }

    public static void main(String[] args) {
        launch();
    }
}