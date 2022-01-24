package com.example.virtualspace;

import com.example.virtualspace.app.VirtualSpace;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    VirtualSpace virtualSpace;

    @Override
    public void start(Stage stage) {
        virtualSpace = new VirtualSpace(stage);
        virtualSpace.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        virtualSpace.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}