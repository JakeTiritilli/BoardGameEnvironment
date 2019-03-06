package application.controllers;

import application.utility.ViewGrabber;
import application.utility.ViewInitializer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Abstract base class to hold common functionality of
 * Menu Controllers. The {@code menuAnchorPane} property
 * must be set in a class that inherits from it before methods
 * are called. This should be done by adding an {@code initialize} method
 * to the subclass that is automatically called when the FXML is loaded.
 */
public abstract class MenuController {
    protected AnchorPane menuAnchorPane; // **Must be initialized first**

    public void loadCheckersContent() throws Exception {
        Pane checkerboardPane = ViewInitializer.initGameboardPane(this);
        loadContent(checkerboardPane);
    }

    public void loadOthelloContent() throws Exception {
        Pane othelloPane = ViewInitializer.initOthelloPane(this);
        loadContent(othelloPane);
    }

    public void loadMemoryContent() throws Exception {
        Pane memoryPane = ViewInitializer.initMemoryPane(this);
        loadContent(memoryPane);
    }

    public void loadTictactoeContent() throws Exception {
        Pane tictactoePane = ViewInitializer.initTicTacToePane(this);
        loadContent(tictactoePane);
    }

    /**
     * Loads the content of a new game or other content pane by
     * clearing the old one and adding the new one in its place.
     * @param contentPane a reference to the new content pane that is
     * to replace the old one
     */
    private void loadContent(Pane contentPane) {
        Pane contentWidgetPane = ViewGrabber.getContentWidgetPane(menuAnchorPane);
        contentWidgetPane.getChildren().clear();
        contentWidgetPane.getChildren().add(contentPane);
    }
}