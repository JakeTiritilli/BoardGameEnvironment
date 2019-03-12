package boardgamekit.utility;

/**
 * This just a marker interface to allow for a common type
 * amongs board game pieces. No methods need to be declared here.
 * Each game that extends {@code BoardGame} should provide an enum
 * which implements this interface and contains cases for each type
 * of piece that can be placed on the board.
 * 
 * @author Jacob Tiritilli
 */
public interface GamePiece {
    // Marker interface: intentionally left empty
}
