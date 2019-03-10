package checkers.utility;

/**
 * Represents a checker piece on the board. Holds the position
 * as well as the color of the checker piece.
 *
 * @author Tyler Vu
 */

public class CheckerPiece {
    public PosTuple position;
    public CheckerPlayer color;
    public boolean isKing;

    public CheckerPiece(int row, int col, CheckerPlayer color) {
        position = new PosTuple(row, col);
        this.color = color;
        this.isKing = false;
    }

    /**
     * Makes this checker piece a king, allowing it to move forward and backwards.
     */
    public void makeKing() {
        this.isKing = true;
    }
}
