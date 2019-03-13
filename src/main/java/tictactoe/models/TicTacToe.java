package tictactoe.models;

import tictactoe.utility.*;
import boardgamekit.BoardGame;
import boardgamekit.players.*;
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
        GamePiece[] tictactoeBoard = gameBoard[0];
        return horizontalWin(tictactoeBoard) || verticalWin(tictactoeBoard) || diagonalWin(tictactoeBoard);
    }

    private boolean horizontalWin(GamePiece[] board) {
        return (board[0] != null && board[0] == board[1] && board[1] == board[2]) ||
               (board[3] != null && board[3] == board[4] && board[4] == board[5]) ||
               (board[6] != null && board[6] == board[7] && board[7] == board[8]);
    }

    private boolean verticalWin(GamePiece[] board) {
        return (board[0] != null && board[0] == board[3] && board[3] == board[6]) ||
               (board[1] != null && board[1] == board[4] && board[4] == board[7]) ||
               (board[2] != null && board[2] == board[5] && board[5] == board[8]);
    }

    private boolean diagonalWin(GamePiece[] board) {
        return (board[0] != null && board[0] == board[4] && board[4] == board[8]) ||
               (board[2] != null && board[2] == board[4] && board[4] == board[6]);
    }
}
