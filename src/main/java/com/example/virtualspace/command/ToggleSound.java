package com.example.virtualspace.command;

import com.example.virtualspace.config.ConfigManager;

public class ToggleSound implements Command {
    @Override
    public void execute() {
        ConfigManager.getInstance().toggleSound();
    }

    @Override
    public void undo() {
        ConfigManager.getInstance().toggleSound();
    }
}
