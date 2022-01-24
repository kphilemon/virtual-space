package com.example.virtualspace.ui;

import com.example.virtualspace.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Layer extends ImageView {
    public Layer(String path, boolean visibility) {
        super(new Image(Main.class.getResource(path).toExternalForm()));
        this.setVisible(visibility);
    }
}
