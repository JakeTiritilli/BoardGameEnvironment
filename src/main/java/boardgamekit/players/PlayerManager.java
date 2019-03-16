package boardgamekit.players;

import javafx.scene.*;

/**
 * PlayerManager
 */
public class PlayerManager {
    
    private Scene scene;
    
    private Player player1;

    private Player player2;

    public PlayerManager(Scene scene) {
        this.scene = scene;
    }

    public void setPlayers(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;
    }

    public void loadGame() {
        // Use view initializer to load game.
    }

    public void loadLogin() {
        // User view initializer to load login screen.
    }
}
