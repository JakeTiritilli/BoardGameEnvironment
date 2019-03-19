package checkers.utility;

import java.util.ArrayList;
import boardgamekit.utility.*;

/**
 * Represents a checker piece on the board. Holds the position
 * as well as the color of the checker piece.
 *
 * @author Tyler Vu
 */

public class CheckerPiece extends GamePiece{
    public CheckerColor color;
    public boolean isKing;

    public CheckerPiece(int row, int col, CheckerColor color) {
        this.setPos(new PosTuple(row,col));
        this.color = color;
        this.isKing = false;
    }

    /**
     * Makes this checker piece a king, allowing it to move forward and backwards.
     */
    public void makeKing() {
        this.isKing = true;
    }

    @Override
    public String toString() {
        return color.toString() + " -> (" + this.getRowNum() + ", " + this.getColNum() + ")";
    }
}
