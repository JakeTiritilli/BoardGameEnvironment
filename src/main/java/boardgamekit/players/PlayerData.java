package boardgamekit.players;

import javafx.beans.property.SimpleStringProperty;

/**
 * Data object to be placed into JavaFX TableView.
 * Holds the player name and how many games they
 * have won of a particular board game.
 * 
 * @author Jacob Tiritilli
 */
public class PlayerData {
    private final SimpleStringProperty playerName;

    private final SimpleStringProperty playerScore;

    PlayerData(String playerName, String playerScore) {
        this.playerName = new SimpleStringProperty(playerName);
        this.playerScore = new SimpleStringProperty(playerScore);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public void setPlayerName(String pName) {
        playerName.set(pName);
    }

    public String getPlayerScore() {
        return playerScore.get();
    }

    public void setPlayerScore(String pScore) {
        playerScore.set(pScore);
    }
}
