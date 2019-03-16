package boardgamekit;

import java.util.ArrayList;

import boardgamekit.players.Player;
import boardgamekit.utility.GamePiece;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * BoardGameController
 * 
 * Common elements:
 * Array to hold board pieces
 * A way to display the current player
 * A way to display the winner
 * A way to display the current score
 * 
 */
public abstract class BoardGameController {

    @FXML
    protected ArrayList<GamePiece> gameBoard;

    @FXML
    protected Label statusLabel;

    @FXML
    protected Label player1Score;

    @FXML
    protected Label player2Score;

    protected Player player1;
    
    protected Player player2;

    public void updateStatus(String text) {
        statusLabel.setText(text);
    }

    public void setPlayer1Score(String score) {
        player1Score.setText(score);
    }

    public void setPlayer2Score(String score) {
        player2Score.setText(score);
    }

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public abstract void initializeGameModel();
}
