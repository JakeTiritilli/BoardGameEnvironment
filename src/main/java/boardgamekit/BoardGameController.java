package boardgamekit;

import java.util.ArrayList;

import boardgamekit.players.Player;
import boardgamekit.utility.GamePiece;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * Abstract class the be provide common functionality to
 * and require certain functionality of all of the board
 * game controllers. This is still a work in progress, and
 * there are many more methods that need to be added.
 * 
 * @author Jacob Tiritilli
 */
public abstract class BoardGameController {

    // Used to hold the board as presented
    // in the view, since each game defines
    // an array list in the FXML to use as the board.
    //
    // THIS SHOULD NOT BE USED YET, AS THERE HAS NOT BEEN
    // A COMMON TYPE FOR EACH PIECE IN THE VIEW DEFINED.
    @FXML
    protected ArrayList<GamePiece> gameBoard;

    // Holds a status labels that can be used to display the
    // state of the game or some other information.
    @FXML
    protected Label statusLabel;

    // Holds the score of player 1 for games
    // that have score in the midddle of the game.
    @FXML
    protected Label player1Score;

    // Holds the score of player 2 for games
    // that have score in the midddle of the game.
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

    /**

     * This method should be used to initialze and instance of the

     * {@code BoardGame} object that represents the game's model.
     * This will be called automatically during the segue after the player
     * login and will ensure that the game is able to be played.
     */
    public abstract void initializeGameModel();

    public abstract void startTurn();

    public void makeMove() {};
}
