package checkers.utility;

import java.util.ArrayList;

/**
 * Represents a checker piece on the board. Holds the position
 * as well as the color of the checker piece.
 *
 * @author Tyler Vu
 */

public class CheckerPiece {
    public PosTuple position;
    public CheckerPlayer color;
    public ArrayList<PosTuple> validMoves;
    public boolean isKing;

    public CheckerPiece(int row, int col, CheckerPlayer color) {
        this.position = new PosTuple(row, col);
        this.color = color;
        this.isKing = false;
        this.validMoves = new ArrayList();
    }

    /**
     * Makes this checker piece a king, allowing it to move forward and backwards.
     */
    public void makeKing() {
        this.isKing = true;
    }

    public void clearValidMoves() {
        this.validMoves = new ArrayList();
    }
}
