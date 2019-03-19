package application.controllers;

import java.util.List;

import boardgamekit.players.PlayerManager;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

/**
 * Serves as the controller for the main and side bar menus.
 * Handles events by swapping panes in and out of the center
 * pane to display different games.
 * 
 * @author Jacob Tiritilli
 */
public class MenuController extends ViewInitializer {

    @FXML
    private List<Button> gameButtons; // Outlet collection of UI buttons

    /**
     * Loads a specific game view into the center pane. This is the handler
     * that is invoked when any of the game load buttons is pressed in the
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
        String loginPath = ViewInitializer.LOGIN;
        String leaderboardPath = ViewInitializer.LEADERBOARD;
        String jsonPath = ViewInitializer.PLAYER_DATA;
        String gamePath = gamesURL[gameNumber];
        String gameName = gameNames[gameNumber];
        PlayerManager playerManager = new PlayerManager(gamePath, loginPath, leaderboardPath, jsonPath, gameName);
        playerManager.loadLogin();
    }
}
