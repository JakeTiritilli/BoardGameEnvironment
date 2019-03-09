package application.controllers;

import java.util.List;

import application.utility.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;

/**
 * Serves as the controller for the main and side bar menus.
 * Handles events by swapping panes in and out of the center
 * pane to display different games.
 * 
 * @author Jacob Tiritilli
 */
public class MenuController {
    // Array of view intializers for each game.
    // The order must match the order of buttons
    // in {@code gameButtons}.
    private ViewInitializer[] gameInitializers = {
        CheckersVI.create(),
        OthelloVI.create(),
        TicTacToeVI.create(),
        MemoryVI.create()
    };

    @FXML
    private List<Button> gameButtons; // Outlet collection of UI buttons

    /**
     * Loads a specific game view into the center pane. This is the handler
     * that is invoked when any of the game load buttons in pressed in the
     * menu. Based on the button that is pressed, the function searches for
     * the corresponding game initializer in {@code gameInitializers} and then
     * swaps out the panes.
     * @param actionEvent the event that caused the function invocation. Used to
     * extract a reference to the Button object that was clicked.
     * @throws Exception if FXML could not be loaded.
     */
    public void loadGameScene(ActionEvent actionEvent) throws Exception {
        Button gameButton = (Button) actionEvent.getSource();
        int gameNumber = gameButtons.indexOf(gameButton);
        ViewInitializer gameInitializer = gameInitializers[gameNumber];
        swapPaneIn(gameInitializer.getPane(), RootVI.swapOutPane);
    }

    /**
     * Event handler called when the button to load the main menu back is
     * pressed.
     * @param actionEvent the event that resulted in the function invocation.
     * @throws Exception if FXML could not be loaded.
     */
    public void loadMainMenuContent(ActionEvent actionEvent) throws Exception{
        Pane mainVI = MainVI.create().getPane();
        swapPaneIn(mainVI, RootVI.swapOutPane);
    }

    /**
     * Swaps a given Pane into a container Pane.
     * @param newPane the Pane to be swapped in
     * @param containerPane the holding Pane that the
     * new pane will be placed into, clearing out
     * what was previously in its place.
     */
    private void swapPaneIn(Pane newPane, Pane containerPane) {
        containerPane.getChildren().clear();
        containerPane.getChildren().add(newPane);
    }

    /**
     * Called when the "Exit" button is pressed. Closes the entire application.
     */
    public void closeApplication() {
        System.exit(0);
    }
}
