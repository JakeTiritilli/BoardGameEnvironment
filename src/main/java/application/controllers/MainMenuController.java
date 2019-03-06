package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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
