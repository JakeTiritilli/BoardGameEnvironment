package boardgamekit.players;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Controller for displaying the leaderboard data in a
 * JavaFX TableView.
 * 
 * @author Jacob Tiritilli
 */
public class LeaderboardController {

    private PlayerLoader playerLoader;

    @FXML
    private Label leaderboardTitle;

    @FXML
    private TableView<PlayerData> playerData;

    @FXML
    private TableColumn<PlayerData, String> playerNameCol;
    
    @FXML
    private TableColumn<PlayerData, String> gamesWonCol;

    /**
     * Initializes the window for a new TableView.
     * @param playerManager the login management class that allows
     * for the segue between the login screen and the actual game.
     */
    public void initLeaderboardController(PlayerManager playerManager) {
        playerLoader = playerManager.getPlayerLoader();
        
        playerNameCol.setCellValueFactory(
            new PropertyValueFactory<PlayerData, String>("playerName")
        );
        
        gamesWonCol.setCellValueFactory(
            new PropertyValueFactory<PlayerData, String>("playerScore")
        );

        String gameLookup = playerManager.getGameName();
        String gameTitle = gameLookup.substring(0, 1).toUpperCase() + gameLookup.substring(1);
        leaderboardTitle.setText(gameTitle + " Leaderboard");
        populateTable(gameLookup);
    }

    /**
     * Fills table with each user that has played the game and how many times
     * they have won.
     * 
     * @param gameLookup the name of the game as stored in the JSON file
     */
    private void populateTable(String gameLookup) {
        final ObservableList<PlayerData> data = FXCollections.observableArrayList();

        try {
            JSONObject json = playerLoader.dumpData();
            
            for (String user : json.keySet()) {
                JSONObject playerStats = json.getJSONObject(user);
                for (String game : playerStats.keySet()) {
                    if (game.equals(gameLookup)) {
                        data.add(new PlayerData(user, String.valueOf(playerStats.getInt(game))));
                    }
                }
            }

            playerData.getItems().setAll(data);
        } catch (IOException error) {
            leaderboardTitle.setText("Error: Failed to find player profiles file.");
        }
    }
}
