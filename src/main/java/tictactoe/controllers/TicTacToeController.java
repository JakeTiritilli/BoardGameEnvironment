package tictactoe.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

import boardgamekit.players.Player;
import tictactoe.models.*;
import tictactoe.utility.*;

/**
 * Represents the contoller for the Tic Tac Toe game
 * in the MVC pattern. Acts as the intermediary between the
 * FXML view and the {@code TicTacToe} model. Contains various
 * actions that are executed when an event is triggerd
 * in the view, and outlets for manipulating objects in the view.
 * 
 * @author Jacob Tiritilli
 */
public class TicTacToeController {

    // Holds the Tic Tac Toe game. Passes in player "X" as
    // the starting player, but this option may be given
    // to the user at a later point.
    private TicTacToe game = new TicTacToe(Player.createDefault(""), Player.createDefault("")); // REPLACE

    // Outlet associated with the label in the view that will
    // display the status of the game.
    public Label statusLabel;

    // Outlet collection of all of the labels that act
    // as cells on the game board in the
    // view.
    @FXML
    private List<Label> cellList;

    /**
     * Automatically called by the system when the FXML is loaded.
     * Performs initial setup by setting the value of the status label
     * and setting an event handler that will be executed when a cell
     * on the game board is clicked with the mouse. The handler is
     * passed both the number of the cell and a reference to that cell's
     * label.
     */
    @FXML
    protected void initialize() {
        for (int i = 0; i < cellList.size(); i++) {
            final int cellNum = i;
            final Label label = cellList.get(i);
            cellList.get(i).setOnMouseClicked(event -> cellPressed(cellNum, label));
        }

        statusLabel.setText("Turn: " + game.getCurrentPlayerPiece().toString());
    }

    /**
     * Event handler that is executed when a given cell on the game board
     * is clicked. It informs the model of the move and updates the view.
     * @param cellNum the number of the cell that was clicked (0-9)
     * @param label a reference to the label of the clicked cell so that it
     * can be updated with the appropriate player
     */
    private void cellPressed(int cellNum, Label label) {
        TicTacToePlayer currentPlayer = (TicTacToePlayer) game.getCurrentPlayerPiece();
        
        // If move was invalid, then exit early.
        try {
            game.makeMove(cellNum, true, true);
        } catch (Exception e) {
            return;
        }

        label.setText(currentPlayer.toString());

        if (game.gameIsWon()) {
            statusLabel.setText("Game Over: Player " + currentPlayer.toString() + " wins!");
        } else if (game.boardIsFull()) {
            statusLabel.setText("Cat's Game!");
        } else {
            statusLabel.setText("Turn: " + game.getCurrentPlayerPiece().toString());
        }
    }

    /**
     * Begins a new Tic Tac Toe game and updates the view to
     * clear the board and status label.
     */
    public void startNewGame(ActionEvent actionEvent) {
        game = new TicTacToe(Player.createDefault(""), Player.createDefault("")); // REPLACE
        statusLabel.setText("Turn: " + game.getCurrentPlayerPiece().toString());
        
        for (Label cell : cellList) {
            cell.setText("");
        }
    }
}
