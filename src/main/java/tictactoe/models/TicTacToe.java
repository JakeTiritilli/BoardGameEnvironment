package tictactoe.models;

import tictactoe.utility.*;
import boardgamekit.BoardGame;
import boardgamekit.players.*;
import boardgamekit.utility.GamePiece;
import boardgamekit.utility.InvalidMoveException;

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

    public void makeMove(int row, int column) throws InvalidMoveException {
        if (gameIsWon()) {
            throw new InvalidMoveException("Attempts to make move after game completed.");
        }

        if (row >= gameBoard.length || column >= gameBoard[0].length) {
            throw new InvalidMoveException("Attempted to place a move out of bounds.");
        }

        if (gameBoard[row][column] != null) {
            throw new InvalidMoveException("Attempted to place a move where a player already is.");
        }

        gameBoard[row][column] = getCurrentPlayerPiece();
        
        switchCurrentPlayer();
    }

    public void makeMove(int position) throws InvalidMoveException {
        makeMove(0, position);
    }

    /**
     * Returns whether there is a piece placed in every cell of the board.
     * @return true if ever cell is occupied on the game board, else false
     */
    public boolean boardIsFull() {
        for (GamePiece[] row : gameBoard) {
            for (GamePiece slot : row) {
                if (slot == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether there is a winning sequence of three in a row
     * anywhere on the game board.
     * @return true if any player has three in a row horizontally,
     * vertically, or diagonally; otherwise, false.
     */
    public boolean gameIsWon() {
        GamePiece[] tictactoeBoard = gameBoard[0];
        boolean isWon = horizontalWin(tictactoeBoard) || verticalWin(tictactoeBoard) || diagonalWin(tictactoeBoard);
        if (isWon) {
            setWinner("tictactoe", true);
        }
        return isWon;
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
