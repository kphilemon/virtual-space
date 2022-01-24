package com.example.virtualspace.ui;

import javafx.scene.control.Button;

public class MenuButton extends Button {
    public MenuButton(String buttonText) {
        super(buttonText);
        this.setMaxWidth(Double.MAX_VALUE);
    }

    public MenuButton() {
        super();
        this.setMaxWidth(Double.MAX_VALUE);
    }
}
