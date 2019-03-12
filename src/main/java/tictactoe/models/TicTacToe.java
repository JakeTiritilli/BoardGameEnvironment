package tictactoe.models;

import tictactoe.utility.*;
import boardgamekit.BoardGame;
import boardgamekit.players.*;
import boardgamekit.utility.Game;
import boardgamekit.utility.GamePiece;

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
        GamePiece[] gameBoard = oneDBoard;
        return (gameBoard[0] != null && gameBoard[0] == gameBoard[1] && gameBoard[1] == gameBoard[2]) ||
               (gameBoard[3] != null && gameBoard[3] == gameBoard[4] && gameBoard[4] == gameBoard[5]) ||
               (gameBoard[6] != null && gameBoard[6] == gameBoard[7] && gameBoard[7] == gameBoard[8]);
    }

    private boolean verticalWin() {
        GamePiece[] gameBoard = oneDBoard;
        return (gameBoard[0] != null && gameBoard[0] == gameBoard[3] && gameBoard[3] == gameBoard[6]) ||
               (gameBoard[1] != null && gameBoard[1] == gameBoard[4] && gameBoard[4] == gameBoard[7]) ||
               (gameBoard[2] != null && gameBoard[2] == gameBoard[5] && gameBoard[5] == gameBoard[8]);
    }

    private boolean diagonalWin() {
        GamePiece[] gameBoard = oneDBoard;
        return (gameBoard[0] != null && gameBoard[0] == gameBoard[4] && gameBoard[4] == gameBoard[8]) ||
               (gameBoard[2] != null && gameBoard[2] == gameBoard[4] && gameBoard[4] == gameBoard[6]);
    }
}
