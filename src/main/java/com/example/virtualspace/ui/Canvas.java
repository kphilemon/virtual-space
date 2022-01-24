package com.example.virtualspace.ui;

import com.example.virtualspace.Main;
import com.example.virtualspace.config.Observer;
import com.example.virtualspace.constants.Decoration;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.HashSet;

public class Canvas extends StackPane implements Observer {
    private HashMap<Decoration, Layer> layers;

    public Canvas() {
        initStyle();
        initLayers();
    }

    @Override
    public void update(boolean isSoundEnabled, HashSet<Decoration> visibleDecorations) {
        for (Decoration decoration : layers.keySet()) {
            layers.get(decoration).setVisible(visibleDecorations.contains(decoration));
        }
    }

    // sets the style of the canvas
    private void initStyle() {
        Image image = new Image(Main.class.getResource("images/canvas_bg.png").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.setBackground(new Background(bgImage));
    }

    // creates all the decoration layers
    private void initLayers() {
        layers = new HashMap<>();
        layers.put(Decoration.Sun, new Layer("images/sun.png", false));
        layers.put(Decoration.Moon, new Layer("images/moon.png", false));
        layers.put(Decoration.Earth, new Layer("images/earth.png", false));
        layers.put(Decoration.Planets, new Layer("images/planets.png", false));
        layers.put(Decoration.Rocket, new Layer("images/rocket.png", false));
        layers.put(Decoration.Asteroids, new Layer("images/asteroids.png", false));
        this.getChildren().addAll(layers.values());
    }
}
