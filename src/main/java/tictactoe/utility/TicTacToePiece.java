package tictactoe.utility;

import boardgamekit.utility.GamePiece;

/**
 * TicTacToePiece
 * 
 * @author Jacob Tiritilli
 */
public class TicTacToePiece extends GamePiece {
    TicTacToePlayer player;

    public static TicTacToePiece X() {
        return new TicTacToePiece(TicTacToePlayer.X);
    }

    public static TicTacToePiece O() {
        return new TicTacToePiece(TicTacToePlayer.O);
    }

    private TicTacToePiece(TicTacToePlayer player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return player.toString();
    }
}