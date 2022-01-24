package com.example.virtualspace.ui;

import com.example.virtualspace.Main;
import com.example.virtualspace.command.Command;
import com.example.virtualspace.command.ToggleDecoVisibility;
import com.example.virtualspace.command.ToggleSound;
import com.example.virtualspace.config.Observer;
import com.example.virtualspace.constants.Decoration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Menu extends GridPane implements Observer {
    private Stack<Command> commandHistory;
    private HashMap<Decoration, MenuButton> decoButtons;
    private MenuButton undoBtn, soundBtn;

    public Menu() {
        commandHistory = new Stack<>();
        initStyle();
        initButtons();
    }

    @Override
    public void update(boolean isSoundEnabled, HashSet<Decoration> visibleDecorations) {
        soundBtn.setText(isSoundEnabled ? "Pause Music" : "Play Music");
        for (Decoration decoration : decoButtons.keySet()) {
            String text = (visibleDecorations.contains(decoration) ? "Remove " : "Add ") + decoration;
            decoButtons.get(decoration).setText(text);
        }
    }

    // sets the style of the menu
    private void initStyle() {
        ColumnConstraints c = new ColumnConstraints();
        c.setHgrow(Priority.ALWAYS);
        this.getColumnConstraints().addAll(c, c, c, c);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
        this.setHgap(10);

        Image image = new Image(Main.class.getResource("images/menu_bg.png").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        this.setBackground(new Background(bgImage));
    }

    // creates all the buttons
    private void initButtons() {
        undoBtn = new MenuButton("Undo");
        undoBtn.setOnAction(e -> undoCommand());
        soundBtn = new MenuButton("Pause Music");
        soundBtn.setOnAction(e -> executeCommand(new ToggleSound()));

        decoButtons = new HashMap<>();
        decoButtons.put(Decoration.Sun, new MenuButton());
        decoButtons.put(Decoration.Moon, new MenuButton());
        decoButtons.put(Decoration.Earth, new MenuButton());
        decoButtons.put(Decoration.Rocket, new MenuButton());
        decoButtons.put(Decoration.Planets, new MenuButton());
        decoButtons.put(Decoration.Asteroids, new MenuButton());
        for (Decoration decoration : decoButtons.keySet()) {
            MenuButton decoBtn = decoButtons.get(decoration);
            decoBtn.setText(decoration.name());
            decoBtn.setOnAction(e -> executeCommand(new ToggleDecoVisibility(decoration)));
        }

        this.addRow(0, decoButtons.get(Decoration.Sun), decoButtons.get(Decoration.Moon), decoButtons.get(Decoration.Earth), undoBtn);
        this.addRow(1, decoButtons.get(Decoration.Rocket), decoButtons.get(Decoration.Planets), decoButtons.get(Decoration.Asteroids), soundBtn);
    }

    private void executeCommand(Command command) {
        command.execute();

        // add to command history only if it is a toggle deco visibility command
        if (command instanceof ToggleDecoVisibility) {
            commandHistory.push(command);
        }
    }

    private void undoCommand() {
        if (commandHistory.isEmpty()) {
            return;
        }

        Command command = commandHistory.pop();
        if (command != null) {
            command.undo();
        }
    }
}
