package com.example.virtualspace.app;

import com.example.virtualspace.Main;
import com.example.virtualspace.config.ConfigManager;
import com.example.virtualspace.config.Observer;
import com.example.virtualspace.constants.Decoration;
import com.example.virtualspace.ui.Canvas;
import com.example.virtualspace.ui.Menu;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashSet;

public class VirtualSpace implements Observer {
    private Stage stage;
    private MediaPlayer mediaPlayer;

    public VirtualSpace(Stage stage) {
        this.stage = stage;
        this.mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("audio/music.mp3").toExternalForm()));
    }

    public void start() {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas();
        Menu menu = new Menu();
        root.setTop(canvas);
        root.setCenter(menu);
        Scene scene = new Scene(root, 700, 620);

        mediaPlayer.setVolume(1);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.registerObserver(canvas);
        configManager.registerObserver(menu);
        configManager.registerObserver(this);
        configManager.notifyObservers();

        stage.setTitle("Virtual Space");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        ConfigManager.getInstance().saveConfigs();
    }

    @Override
    public void update(boolean isSoundEnabled, HashSet<Decoration> visibleDecorations) {
        if (isSoundEnabled) {
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
    }
}
