package com.example.virtualspace.config;

import com.example.virtualspace.constants.Decoration;

import java.util.HashSet;

public interface Observer {
    public void update(boolean isSoundEnabled, HashSet<Decoration> visibleDecorations);
}
