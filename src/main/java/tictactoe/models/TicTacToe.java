package tictactoe.models;

import tictactoe.utility.*;
import boardgamekit.BoardGame;
import boardgamekit.players.*;

/**
 * Represents a model for and contains general functionality for playing the
 * game of TicTacToe.
 * 
 * @author Jacob Tiritilli
 */
public class TicTacToe extends BoardGame {

    public TicTacToe(Player p1, Player p2) {
        super(p1, p2, 9);
        setPlayer1GamePiece(TicTacToePlayer.X);
        setPlayer2GamePiece(TicTacToePlayer.O);
    }

    /**
     * Checks whether there is a winning sequence of three in a row
     * anywhere on the game board.
     * @return true if any player has three in a row horizontally,
     * vertically, or diagonally; otherwise, false.
     */
    public boolean gameIsWon() {
        return horizontalWin() || verticalWin() || diagonalWin();
    }

    private boolean horizontalWin() {
        return (oneDBoard[0] != null && oneDBoard[0] == oneDBoard[1] && oneDBoard[1] == oneDBoard[2]) ||
               (oneDBoard[3] != null && oneDBoard[3] == oneDBoard[4] && oneDBoard[4] == oneDBoard[5]) ||
               (oneDBoard[6] != null && oneDBoard[6] == oneDBoard[7] && oneDBoard[7] == oneDBoard[8]);
    }

    private boolean verticalWin() {
        return (oneDBoard[0] != null && oneDBoard[0] == oneDBoard[3] && oneDBoard[3] == oneDBoard[6]) ||
               (oneDBoard[1] != null && oneDBoard[1] == oneDBoard[4] && oneDBoard[4] == oneDBoard[7]) ||
               (oneDBoard[2] != null && oneDBoard[2] == oneDBoard[5] && oneDBoard[5] == oneDBoard[8]);
    }

    private boolean diagonalWin() {
        return (oneDBoard[0] != null && oneDBoard[0] == oneDBoard[4] && oneDBoard[4] == oneDBoard[8]) ||
               (oneDBoard[2] != null && oneDBoard[2] == oneDBoard[4] && oneDBoard[4] == oneDBoard[6]);
    }
}
