package checkers.models;

import checkers.utility.CheckerPiece;
import checkers.utility.CheckerPlayer;
import checkers.utility.PosTuple;
import java.util.ArrayList;

/**
 * Represents the model of the game checkers. Serves as an engine in order
 * to run the logic of the game
 *
 * @author Tyler Vu
 */

public class Checkers {
    private CheckerPlayer currentPlayer;
    private CheckerPiece[][] gameBoard = new CheckerPiece[8][8];
    private ArrayList<CheckerPiece> blackCheckerPieceRefs = new ArrayList<>();
    private ArrayList<CheckerPiece> redCheckerPieceRefs = new ArrayList<>();

    public Checkers() {
        this.initializeGame();

        // testing
        this.getMovableCheckerPiecesForActivePlayer();
    }

    /**
     * Toggles the current player.
     */
    public void changeTurn() {
        this.currentPlayer = this.currentPlayer == CheckerPlayer.BLACK ? CheckerPlayer.RED : CheckerPlayer.BLACK;
    }

    /**
     * Gets the checker pieces that are able to make a valid move for the current
     * player.
     * @return ArrayList of checker pieces that are able to make a valid move.
     */
    public ArrayList<CheckerPiece> getMovableCheckerPiecesForActivePlayer() {
        if (this.currentPlayer == CheckerPlayer.BLACK) {
            return this.getMovableCheckerPieces(blackCheckerPieceRefs);
        }
        return this.getMovableCheckerPieces(redCheckerPieceRefs);
    }

    /**
     * Iterates through a list of checker pieces and checks to see if they can
     * make a move. Returns a list of the checkers that can make a valid move.
     * @param refs List of checkers
     * @return List of checkers that can make a valid move
     */
    private ArrayList<CheckerPiece> getMovableCheckerPieces(ArrayList<CheckerPiece> refs) {
        ArrayList<CheckerPiece> result = new ArrayList<>();
        for(CheckerPiece checker : refs) {
            if(this.getValidMovesForChecker(checker).size() != 0) {
                result.add(checker);
            }
        }
        return result;
    }

    /**
     * Gets the valid moves for a single checker piece.
     * @param checker CheckerPiece
     * @return List of PosTuples
     */
    private ArrayList<PosTuple> getValidMovesForChecker(CheckerPiece checker) {
        ArrayList<PosTuple> validMoves = new ArrayList<>();
        ArrayList<PosTuple> surroundingPlayableCells = this.getSurroundingPlayableCells(checker);

        //Check valid moves for surrounding playable cells
        for(PosTuple pos: surroundingPlayableCells) {
            if(this.isValidMoveForChecker(checker, pos)) {
                validMoves.add(pos);
            }
        }
        return validMoves;
    }

    // TODO: Implement
    /**
     * Checks if the checker can make the move to the position. This takes into account whether it is a king or not.
     * If the position is 2 rows away, it will check to see if there is an enemy checker in the intermediate cell.
     * @param checker CheckerPiece
     * @param pos PosTuple
     * @return Boolean
     */
    private boolean isValidMoveForChecker(CheckerPiece checker, PosTuple pos) {
    	// get all valid moves
    	ArrayList<PosTuple> moves = getSurroundingPlayableCells(checker);
		// check for enemy
    	boolean enemy = false;
		
    	if(moves.contains(pos))
		{
    		// checker and pos are 2 rows away  
    		if( Math.abs(checker.position.row - pos.row) > 1)
    		{
    			// CHECK enemy for red player
    			if(checker.color == CheckerPlayer.RED)
    			{
    				 // down one row right col
    				if(gameBoard[checker.position.row+1][checker.position.col+1] != null)
    					enemy= true;
    				// down one row left col
    				else if(gameBoard[checker.position.row+1][checker.position.col-1] != null)
    					enemy = true;
    			}
    			// check enemy for black player
    			else 
    			{
    				 // up one row right col
    				if(gameBoard[checker.position.row-1][checker.position.col+1] != null)
    					enemy= true;
    				// up one row left col
    				else if(gameBoard[checker.position.row-1][checker.position.col-1] != null)
    					enemy = true;
    			}
    		}
			return true;
		}
    	if(checker.isKing)
		{
			// handleKing()
		}
        return false;
    }

    /**
     * Checks in every playable direction based on the param checker piece's position
     * to see if there are playable cells for that checker. (This does not check if the
     * surrounding cells are a valid move. Only checks if they are playable.)
     * @param checker
     * @return
     */
    private ArrayList<PosTuple> getSurroundingPlayableCells(CheckerPiece checker) {
        ArrayList<PosTuple> result = new ArrayList<>();

        // Create PosTuples for every direction from the checker position.
        // Includes cells that would be playable if there was a valid jump available.
        ArrayList<PosTuple> directionsToCheck = new ArrayList<PosTuple>() {{
            add(new PosTuple(checker.position.row + 1, checker.position.col - 1));
            add(new PosTuple(checker.position.row + 1, checker.position.col + 1));
            add(new PosTuple(checker.position.row - 1, checker.position.col - 1));
            add(new PosTuple(checker.position.row - 1, checker.position.col + 1));
            add(new PosTuple(checker.position.row + 2, checker.position.col - 2));
            add(new PosTuple(checker.position.row + 2, checker.position.col + 2));
            add(new PosTuple(checker.position.row - 2, checker.position.col - 2));
            add(new PosTuple(checker.position.row - 2, checker.position.col + 2));
        }};

        for (PosTuple pos : directionsToCheck) {
            if (this.isValidPos(pos)) {
                result.add(pos);
            }
        }
        return result;
    }

    /**
     * Checks to make sure that the param position is not outside of the bounds
     * of the board.
     * @param pos PosTuple
     * @return boolean
     */
    private boolean isValidPos(PosTuple pos) {
        return pos.row >= 0 && pos.row <= 7 && pos.col >= 0 && pos.col <= 7;
    }

    /**
     * Sets the current player as black because the black player always goes first.
     * Populates the gameBoard private variable with null values and the CheckerPlayer
     * enums based on the positions.
     */
    private void initializeGame() {
        this.currentPlayer = CheckerPlayer.BLACK; // Black always goes first
        this.initializeGameBoard();
        this.initializeGamePieces();
    }

    /**
     * Inserts null values into each cell of the double array to
     * "initialize" the game board.
     */
    private void initializeGameBoard() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                gameBoard[i][j] = null;
            }
        }
    }

    /**
     * Inserts CheckerPlayer.RED and CheckerPlayer.BLACK in the
     * correct cells.
     */
    private void initializeGamePieces() {
        this.initializeRedGamePieces();
        this.initializeBlackGamePieces();
    }

    /**
     * Loops through the top 3 rows and inserts CheckerPiece objects into
     * the valid cells. Adds the references of the objects to their
     * reference list (redCheckerPieceRefs).
     */
    private void initializeRedGamePieces() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 8; j++) {
                // Places the red enum on the correct cells and adds it to reference list
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    CheckerPiece pieceToInsert = new CheckerPiece(i, j, CheckerPlayer.RED);
                    gameBoard[i][j] = pieceToInsert;
                    this.redCheckerPieceRefs.add(pieceToInsert);
                }
            }
        }
    }

    /**
     * Loops through the bottom 3 rows and inserts CheckerPiece objects into
     * the valid cells. Adds the references of the objects to their respective
     * reference list (blackCheckerPieceRefs).
     */
    private void initializeBlackGamePieces() {
        for(int i = 5; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                // Places the black enum on the correct cells and adds it to reference list
                if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                    CheckerPiece pieceToInsert = new CheckerPiece(i, j, CheckerPlayer.BLACK);
                    gameBoard[i][j] = pieceToInsert;
                    this.blackCheckerPieceRefs.add(pieceToInsert);
                }
            }
        }
    }
}
