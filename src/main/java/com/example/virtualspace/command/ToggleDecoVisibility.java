package com.example.virtualspace.command;

import com.example.virtualspace.config.ConfigManager;
import com.example.virtualspace.constants.Decoration;

public class ToggleDecoVisibility implements Command {
    private Decoration decoration;

    public ToggleDecoVisibility(Decoration decoration) {
        this.decoration = decoration;
    }

    @Override
    public void execute() {
        ConfigManager.getInstance().toggleDecoVisibility(decoration);
    }

    @Override
    public void undo() {
        ConfigManager.getInstance().toggleDecoVisibility(decoration);
    }
}
