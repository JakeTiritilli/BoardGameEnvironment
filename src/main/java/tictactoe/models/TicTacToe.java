package tictactoe.models;

import tictactoe.utility.*;

/**
 * Represents a model for and contains general functionality for playing the
 * game of TicTacToe.
 * 
 * @author Jacob Tiritilli
 */
public class TicTacToe {

    private TicTacToePlayer currentPlayer;

    // Fixed size game board of TicTacToePlayer enums (either X or O).
    // A value of `null` means that the cell is open.
    private TicTacToePlayer[] gameBoard = new TicTacToePlayer[9];

    /**
     * Constructs a new TicTacToe object and sets the
     * current player to the player provided to be the
     * starting player.
     * @param firstPlayer represents the player that will start
     * the game (either X or O).
     */
    public TicTacToe(TicTacToePlayer firstPlayer) {
        currentPlayer = firstPlayer;
    }

    /**
     * Returns the current player at any time in the game state.
     * @return the player whose turn it currently is (either X or O).
     */
    public TicTacToePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Makes a move on the game board at a given position.
     * @param boardPosition the position on the board to move into (0-9)
     * @return true if the cell is valid (i.e., on the board and not occupied)
     * and the game is not over; otherwise, false.
     */
    public boolean makeMove(int boardPosition) {
        if (!gameWon() && gameBoard[boardPosition] == null) {
            gameBoard[boardPosition] = currentPlayer;
            currentPlayer = (currentPlayer == TicTacToePlayer.X) ? TicTacToePlayer.O : TicTacToePlayer.X;
            return true;
        }
        return false;
    }

    /**
     * Checks whether there is a winning sequence of three in a row
     * anywhere on the game board.
     * @return true if any player has three in a row horizontally,
     * vertically, or diagonally; otherwise, false.
     */
    public boolean gameWon() {
        return horizontalWin() || verticalWin() || diagonalWin();
    }

    /**
     * Checks whether the board is completley full or not
     * (i.e., whether there are no open cells).
     * @return true if none of the cells in the game board
     * are null; otherwise, false.
     */
    public boolean boardIsFull() {
        for (TicTacToePlayer slot : gameBoard) {
            if (slot == null) {
                return false;
            }
        }
        return true;
    }

    private boolean horizontalWin() {
        return (gameBoard[0] != null && gameBoard[0] == gameBoard[1] && gameBoard[1] == gameBoard[2]) ||
               (gameBoard[3] != null && gameBoard[3] == gameBoard[4] && gameBoard[4] == gameBoard[5]) ||
               (gameBoard[6] != null && gameBoard[6] == gameBoard[7] && gameBoard[7] == gameBoard[8]);
    }

    private boolean verticalWin() {
        return (gameBoard[0] != null && gameBoard[0] == gameBoard[3] && gameBoard[3] == gameBoard[6]) ||
               (gameBoard[1] != null && gameBoard[1] == gameBoard[4] && gameBoard[4] == gameBoard[7]) ||
               (gameBoard[2] != null && gameBoard[2] == gameBoard[5] && gameBoard[5] == gameBoard[8]);
    }

    private boolean diagonalWin() {
        return (gameBoard[0] != null && gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]) ||
               (gameBoard[2] != null && gameBoard[2] == gameBoard[4] && gameBoard[4] == gameBoard[6]);
    }
}
