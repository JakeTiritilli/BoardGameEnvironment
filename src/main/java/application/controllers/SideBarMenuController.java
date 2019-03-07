package application.controllers;

import application.utility.ViewGrabber;
import application.utility.ViewInitializer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SideBarMenuController extends MenuController {
    @FXML
    private AnchorPane sideBarMenuAnchorPane;

    /**
     * Called automatically by the system and
     * intializes the menu anchor pane in
     * {@code MenuController}.
     */
    @FXML
    protected void initialize() {
        menuAnchorPane = sideBarMenuAnchorPane;
    }

    /**
     * Called when the "Main Menu" button is pressed. Initializes the main menu view and
     * inserts it into the main content widget.
     * @throws Exception From initMainMenuPane()
     */
    public void loadMainMenuContent() throws Exception {
        AnchorPane mainMenuPane = ViewInitializer.initMainMenuPane(this);
        Pane contentWidgetPane = ViewGrabber.getContentWidgetPane(sideBarMenuAnchorPane);
        contentWidgetPane.getChildren().clear();
        contentWidgetPane.getChildren().add(mainMenuPane);
    }

    /**
     * Called when the "Exit" button is pressed. Closes the entire application.
     */
    public void closeApplication() {
        Stage stage = (Stage) sideBarMenuAnchorPane.getScene().getWindow();
        stage.close();
    }
}
