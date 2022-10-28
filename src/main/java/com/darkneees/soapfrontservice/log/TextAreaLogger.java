package com.darkneees.soapfrontservice.log;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

public class TextAreaLogger extends OutputStream {

    private final TextArea textArea;

    public TextAreaLogger(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        CompletableFuture.runAsync(() -> {
            textArea.appendText(String.valueOf((char) b));
        }, Platform::runLater);
    }
}
