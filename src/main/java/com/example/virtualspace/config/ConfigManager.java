package com.example.virtualspace.config;

import com.example.virtualspace.constants.Decoration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

public class ConfigManager implements Subject {
    private final String APP_CONFIG_FILE = "app.properties";
    private final String DECO_CONFIG_FILE = "decorations.ser";
    private final String SOUND_PROPERTY_KEY = "Sound";

    private static ConfigManager uniqueInstance;
    private ArrayList<Observer> observers;
    private boolean isSoundEnabled;
    private HashSet<Decoration> visibleDecorations;

    private ConfigManager() {
        observers = new ArrayList<>();
        loadSoundConfig();
        loadDecorationsConfig();
    }

    public static synchronized ConfigManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ConfigManager();
        }
        return uniqueInstance;
    }

    public void saveConfigs() {
        saveSoundConfig();
        saveDecorationsConfig();
    }

    public void toggleSound() {
        isSoundEnabled = !isSoundEnabled;
        notifyObservers();
    }

    public void toggleDecoVisibility(Decoration decoration) {
        if (visibleDecorations.contains(decoration)) {
            visibleDecorations.remove(decoration);
        } else {
            visibleDecorations.add(decoration);
        }
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(isSoundEnabled, visibleDecorations);
        }
    }

    private void loadSoundConfig() {
        isSoundEnabled = true;

        File file = new File(APP_CONFIG_FILE);
        if (file.exists()) {
            try (InputStream input = new FileInputStream(file)) {
                Properties p = new Properties();
                p.load(input);
                String value = p.getProperty(SOUND_PROPERTY_KEY);
                if (value != null && value.equalsIgnoreCase("OFF")) {
                    isSoundEnabled = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveSoundConfig() {
        File file = new File(APP_CONFIG_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (OutputStream out = new FileOutputStream(file)) {
            Properties p = new Properties();
            p.setProperty(SOUND_PROPERTY_KEY, isSoundEnabled ? "ON" : "OFF");
            p.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadDecorationsConfig() {
        visibleDecorations = new HashSet<>();

        File file = new File(DECO_CONFIG_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                visibleDecorations = (HashSet<Decoration>) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDecorationsConfig() {
        File file = new File(DECO_CONFIG_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(visibleDecorations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
