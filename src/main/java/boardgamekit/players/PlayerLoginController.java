package boardgamekit.players;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.*;

/**
 * Serves as a controller for the player login page
 * of any game.
 * 
 * @author Jacob Tiritilli
 */
public class PlayerLoginController {

    private PlayerLoader playerLoader;

     // Displays error messages if, for example,
     // this JSON file could not be loaded
     // or no name is entered.
    @FXML
    private Label statusLabel;

    @FXML
    private TextField player1TF;

    @FXML
    private TextField player2TF;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        try {
            playerLoader = new PlayerLoader("src/main/resources/json/UserData.json");
        } catch (IOException error) {
            statusLabel.setText("Error: Failed to find player profiles file.");
        }
    }

    /**
     * Initializes the controller by setting the action
     * to be executed when the login button is pressed.
     * @param playerManager the login management class that allows
     * for the segue between the login screen and the actual game.
     */
    public void initGameController(PlayerManager playerManager) {
        loginButton.setOnAction(
            (event) -> {
                Player p1 = loginPlayer(player1TF);
                if (p1 == null) { return; }
                Player p2 = loginPlayer(player2TF);
                if (p2 == null) { return; }
                playerManager.setPlayers(p1, p2);
                playerManager.loadGame();
            }
        );
    }

    /**
     * Attempts to login a given player, checking
     * for that input has been made.
     * @param playerName a reference to the textfield
     * containing a player's name.
     * @return the player's data
     */
    public Player loginPlayer(TextField playerName) {
        String player = playerName.getText();

        if (player == null || player.isEmpty()) {
            statusLabel.setText("Must enter usernames for both players.");
            return null;
        }

        return retrievePlayerData(player);
    }

    /**
     * Loads the player data from the JSON file, if already
     * present, and creates a new {@code Player} object. If the
     * player is new, then a new {@code Player} object is created.
     * @param player the name of the player whose data is to be
     * loaded from the JSON file.
     * @return the player's data
     */
    private Player retrievePlayerData(String player) {
        if (playerLoader == null) { return null; }

        try {
            ArrayList<String> players = playerLoader.getPlayers();

            if (players.contains(player)) {
                return playerLoader.loadData(player);
            }
            
            return Player.createDefault(player);

        } catch (IOException error) {
            statusLabel.setText("Error loading player data.");
            return null;
        }
    }
}
