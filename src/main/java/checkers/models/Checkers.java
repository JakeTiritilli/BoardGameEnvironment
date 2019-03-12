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
    private CheckerPiece[][] gameBoard = new CheckerPiece[8][8];
    private ArrayList<CheckerPiece> blackCheckerPieceRefs = new ArrayList<>();
    private ArrayList<CheckerPiece> redCheckerPieceRefs = new ArrayList<>();

    // Variables for turn execution
    private CheckerPlayer currentPlayer;
    private boolean jumping = false;
    private CheckerPiece jumpingCheckerPiece = null;


    public Checkers() {
        this.initializeGame();
    }

    /**
     * Gets the checker pieces that are able to make a valid move for the current
     * player.
     * @return ArrayList of checker pieces that are able to make a valid move.
     */
    public ArrayList<CheckerPiece> getMovableCheckerPiecesForActivePlayer() {
        if (this.currentPlayer == CheckerPlayer.BLACK) {
            return this.getMovableCheckerPieces(this.blackCheckerPieceRefs);
        }
        return this.getMovableCheckerPieces(this.redCheckerPieceRefs);
    }

    /**
     * Checks to make sure the move is valid and then performs the move. It will set
     * the checker to be placed into the new cell. If it was a single row move, it will
     * change the player turn. Else, it will keep the same player and delete the checker
     * that got jumped over.
     * @param oldPosition
     * @param newPosition
     */
    public void makeMove(PosTuple oldPosition, PosTuple newPosition) {
        // (Check to make sure that the old pos has a checker there and the new pos
        // does not have a checker there) and (make sure both positions are on the board).
        if ((gameBoard[oldPosition.row][oldPosition.col] == null ||
            gameBoard[newPosition.row][newPosition.col] != null) &&
                (isValidPos(oldPosition) && isValidPos(newPosition))) {
            throw new IllegalArgumentException("The position parameters in makeMove were not valid moves");
        }

        // If the move was a single row move (which means it wasn't a jump),
        // then change the current player
        if (Math.abs(oldPosition.row - newPosition.row) == 1) {
            this.changeTurn();
        }
        // Else, there was a jump so delete the checker piece on the board and in refs
        else {
            int enemyRow = (oldPosition.row + newPosition.row) / 2;
            int enemyCol = (oldPosition.col + newPosition.col) / 2;
            this.deleteCheckerPiece(enemyRow, enemyCol);
            this.setJumpRules(this.gameBoard[newPosition.row][newPosition.col]);
        }

        gameBoard[newPosition.row][newPosition.col] = gameBoard[oldPosition.row][oldPosition.col];
        gameBoard[oldPosition.row][oldPosition.col] = null;
    }

    public void clearValidMovesForAllCheckerPieces() {
        this.clearValidMovesForCheckerPieces(this.blackCheckerPieceRefs);
        this.clearValidMovesForCheckerPieces(this.redCheckerPieceRefs);
    }

    /**
     * Toggles the current player.
     */
    private void changeTurn() {
        this.currentPlayer = this.currentPlayer == CheckerPlayer.BLACK ? CheckerPlayer.RED : CheckerPlayer.BLACK;
    }

    private void clearValidMovesForCheckerPieces(ArrayList<CheckerPiece> checkerPieces) {
        for(CheckerPiece checker: checkerPieces) {
            checker.clearValidMoves();
        }
    }

    private void deleteCheckerPiece(int row, int col) {
        CheckerPiece checkerToDelete = this.gameBoard[row][col];
        CheckerPlayer enemyColor = checkerToDelete.color;

        if(enemyColor == CheckerPlayer.BLACK) {
            this.blackCheckerPieceRefs.remove(this.blackCheckerPieceRefs.indexOf(checkerToDelete));
        }
        else {
            this.redCheckerPieceRefs.remove(this.redCheckerPieceRefs.indexOf(checkerToDelete));
        }

        this.gameBoard[row][col] = null;
    }

    private void setJumpRules(CheckerPiece checker) {
        // Set the jumping boolean to true so that it restricts player movement
        this.jumping = true;

        // Set the jumping checker piece so that only that checker piece is checked
        // for valid moves on next turn. The same player should still have the option
        // to go if there are more valid moves after the jump for that single checker.
        this.jumpingCheckerPiece = checker;
    }

    /**
     * Iterates through a list of checker pieces and checks to see if they can
     * make a move. Returns a list of the checkers that can make a valid move.
     * @param refs List of checkers
     * @return List of checkers that can make a valid move
     */
    private ArrayList<CheckerPiece> getMovableCheckerPieces(ArrayList<CheckerPiece> refs) {
        ArrayList<CheckerPiece> result = new ArrayList<>();

        // If the checker is jumping, then restrict movement to only that checker
        if (this.jumping) {
            if (this.getValidMovesForChecker(this.jumpingCheckerPiece).size() != 0) {
                result.add(this.jumpingCheckerPiece);
            }
            // No more valid jumps available so change turn
            else {
                this.changeTurn();
            }
            // Don't allow further execution because only checking for the jumping piece
            return result;
        }

        for(CheckerPiece checker : refs) {
            if(this.getValidMovesForChecker(checker).size() != 0) {
                result.add(checker);
            }
        }

        // If there are valid moves for the current player, change the turn.
        // The result will return with size 0 so the controller will know to
        // restart the turn with the new active player.
        if(result.size() == 0) {
            this.changeTurn();
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

        // Sets the array of valid moves in the CheckerPiece object
        checker.validMoves = validMoves;
        return validMoves;
    }

    // TODO: Handle King
    /**
     * Checks if the checker can make the move to the position. This takes into account whether it is a king or not.
     * If the position is 2 rows away, it will check to see if there is an enemy checker in the intermediate cell.
     * @param checker CheckerPiece
     * @param pos PosTuple
     * @return Boolean
     */
    private boolean isValidMoveForChecker(CheckerPiece checker, PosTuple pos) {
    	// if pos is only 1 row away, check if it is a valid move for the color
        if (Math.abs(checker.position.row - pos.row) == 1) {
            return this.isSingleRowMoveValid(checker, pos);
        }

        // if pos is 2 rows away, check if there is a jump available
        // can assume that it is two rows away if reached this point of flow
        return this.isJumpMoveValid(checker, pos);
    }

    /**
     * Checks to see if the single row move is valid for the specified CheckerPiece
     * to the specified position.
     * @param checker CheckerPiece being checked for the move
     * @param pos PosTuple that represents the position being checked for a valid move
     * @return Returns true if the single row move is valid
     */
    private boolean isSingleRowMoveValid(CheckerPiece checker, PosTuple pos) {
        // TODO: handle King state
        // Gets the row delta that would be valid for the color
        int rowDelta = checker.color == CheckerPlayer.BLACK ? checker.position.row - 1 : checker.position.row + 1;

        // if the pos being checked has a valid row delta, then check if cell is empty
        if (pos.row == rowDelta) {
            return this.gameBoard[pos.row][pos.col] == null;
        }
        return false;
    }

    /**
     * Checks to see if the specified checker piece can make the jump move to the
     * specified position.
     * @param checker The CheckerPiece that is being checked for the move
     * @param pos A PosTuple that represents the position that the checker is checking to move to
     * @return Returns true if the jump move is valid
     */
    private boolean isJumpMoveValid(CheckerPiece checker, PosTuple pos) {
        int rowDelta;
        int enemyRow;
        int enemyCol;

        // TODO: handle King state
        // sets the row delta that would be valid for the color
        // sets the row that an enemy could possibly be on for the jump
        if (checker.color == CheckerPlayer.BLACK) {
            rowDelta = checker.position.row - 2;
            enemyRow = checker.position.row -1;

        }
        else {
            rowDelta = checker.position.row + 2;
            enemyRow = checker.position.row + 1;
        }

        // sets the var to the intermediate cell between the checker and final pos
        enemyCol = (checker.position.col + pos.col) / 2;

        // if the pos being checked is a valid move for that color, check for enemy
        if (pos.row == rowDelta) {
            return this.hasEnemy(checker, enemyRow, enemyCol);
        }
        return false;
    }

    /**
     * Checks to see if there is an enemy at the position (enemyRow, enemyCol).
     * @param checker CheckerPiece used to check for the opposing color
     * @param enemyRow The row of the cell to be checked for an enemy
     * @param enemyCol The col of the cell to be checked for an enemy
     * @return If there is an enemy there, return true
     */
    private boolean hasEnemy(CheckerPiece checker, int enemyRow, int enemyCol) {
        CheckerPlayer colorToCheckFor = checker.color == CheckerPlayer.BLACK ? CheckerPlayer.RED : CheckerPlayer.BLACK;
        CheckerPiece cellToCheck = this.gameBoard[enemyRow][enemyCol];

        return cellToCheck != null && cellToCheck.color == colorToCheckFor;
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
