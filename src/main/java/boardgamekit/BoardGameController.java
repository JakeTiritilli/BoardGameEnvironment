package boardgamekit;

import java.util.ArrayList;

import boardgamekit.utility.GamePiece;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

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
public abstract class BoardGameController implements Initializable {

    @FXML
    ArrayList<GamePiece> gameBoard;

    @FXML
    Label statusLabel;

    @FXML
    Label player1Score;

    @FXML
    Label player2Score;

    @FXML
    public void initialize() {
        System.out.println("");
    }

    public void updateStatus(String text) {
        statusLabel.setText(text);
    }

    public void setPlayer1Score(String score) {
        player1Score.setText(score);
    }

    public void setPlayer2Score(String score) {
        player2Score.setText(score);
    }
}
