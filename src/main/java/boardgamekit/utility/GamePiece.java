package boardgamekit.utility;

import java.util.ArrayList;

/**
 * Abstract class representing a game piece. This class holds a reference to a
 * player, which will most likely be an enum that implements the
 * {@code GamePlayer} interface.
 * 
 * @author Jacob Tiritilli
 */
public abstract class GamePiece {

    // Holds the position of the pieces.
    private PosTuple piecePos;

    // Holds all of the valid moves that the piece could make.
    private ArrayList<PosTuple> validMoves = new ArrayList<>();

    public int getRowNum() {
        return piecePos.row;
    }

    public int getColNum() {
        return piecePos.col;
    }

    public void setRowNum(int rowNum) {
        piecePos.row = rowNum;
    }

    public void setColNum(int colNum) {
        piecePos.col = colNum;
    }

    public PosTuple getPiecePos() {
        return piecePos;
    }

    public void updatePiecePos(int row, int col) {
        piecePos.row = row;
        piecePos.col = col;
    }

    public void clearValidMoves() {
        validMoves.clear();
    }

    public void addValidMove(int row, int col) {
        validMoves.add(new PosTuple(row, col));
    }

    public ArrayList<PosTuple> getValidMoves() {
        return validMoves;
    }

    /**
     * Should return a representation of the piece as a string.
     * If the subclass holds an enum representing a game player,
     * then this method could just call {@code toString()} on that
     * enum.
     * @return a string representation of the piece
     */
    @Override
    public abstract String toString();
}
