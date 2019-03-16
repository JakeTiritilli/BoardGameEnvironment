package boardgamekit.players;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.*;

/**
 * PlayerLoginController
 */
public class PlayerLoginController {

    private PlayerLoader playerLoader;

    @FXML
    private Label statusLabel;

    @FXML
    private Label player1TF;

    @FXML
    private Label player2TF;

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

    public void initGameController(PlayerManager playerManager) {
        loginButton.setOnAction(
            (event) -> {
                Player p1 = loginPlayer(player1TF);
                Player p2 = loginPlayer(player2TF);
                if (p1 == null || p2 == null) { return; }
                playerManager.setPlayers(p1, p2);
                playerManager.loadGame();
            }
        );
    }

    public Player loginPlayer(Label playerName) {
        String player = playerName.getText();

        if (player.isEmpty()) {
            statusLabel.setText("Must enter usernames for both players.");
            return null;
        }

        return retrievePlayerData(player);
    }

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
