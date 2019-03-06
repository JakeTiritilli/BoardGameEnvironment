package application.controllers;

import application.utility.ViewGrabber;
import application.utility.ViewInitializer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainMenuController extends MenuController {
    @FXML
    private Button checkersPlayerButton;

    @FXML
    private AnchorPane mainMenuAnchorPane;

    /**
     * Called automatically by the system and
     * intializes the menu anchor pane in
     * {@code MenuController}.
     */
    @FXML
    protected void initialize() {
        menuAnchorPane = mainMenuAnchorPane;
    }
}
