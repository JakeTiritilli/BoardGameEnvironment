package checkers.models;

import boardgamekit.BoardGame;
import boardgamekit.players.Player;
import checkers.utility.CheckerColor;
import checkers.utility.CheckerPiece;
import boardgamekit.utility.*;

import java.util.ArrayList;

/**
 * Represents the model of the game checkers. Serves as an engine in order
 * to run the logic of the game
 *d
 * @author Tyler Vu
 */

public class Checkers extends BoardGame {
    private ArrayList<CheckerPiece> blackCheckerPieceRefs = new ArrayList<>();
    private ArrayList<CheckerPiece> redCheckerPieceRefs = new ArrayList<>();

    // Variables for turn execution
    private boolean jumping = false;
    private CheckerPiece jumpingCheckerPiece = null;


    public Checkers(Player player1, Player player2) {
        super(player1, player2, 8, 8);
        this.initializeGame();
    }

    @Override
    public boolean gameIsWon() {
        return getWinConditionStatus() != null;
    }

    @Override
    public void makeMove(int row, int col) {
        PosTuple oldPosition = this.getCurrentPlayerPiece().getPiecePos().copy();
        PosTuple newPosition = new PosTuple(row, col);

        this.moveCheck(oldPosition, newPosition);
        this.handleMoveConditions(oldPosition, newPosition);
        this.updateModelForMove(oldPosition, newPosition);
    }

    /**
     * Gets the checker pieces that are able to make a valid move for the current
     * player.
     * @return ArrayList of checker pieces that are able to make a valid move.
     */
    public ArrayList<CheckerPiece> getMovableCheckerPiecesForActivePlayer() {
        // Have to do this because the gameboardkit does not support holding CheckerColor's
        // Used to derive the color of the player
        CheckerPiece currentPlayerCheckerPlaceholder = (CheckerPiece) this.getCurrentPlayerPiece();

        if (currentPlayerCheckerPlaceholder.color == CheckerColor.BLACK) {
            return this.getMovableCheckerPieces(this.blackCheckerPieceRefs);
        }
        return this.getMovableCheckerPieces(this.redCheckerPieceRefs);
    }

    /**
     * Gets the win condition status for the game state. If the size of one of
     * the checker piece reference lists is 0, that means that the player has lost.
     * @return CheckerColor.RED, CheckerColor.BLACK, or null
     */
    public CheckerColor getWinConditionStatus() {
        if (this.blackCheckerPieceRefs.size() == 0) {
            return CheckerColor.RED;
        }
        else if (this.redCheckerPieceRefs.size() == 0) {
            return CheckerColor.BLACK;
        }
        else {
            return null;
        }
    }

    /**
     * Returns the score for each player. Score is derived from the checker color reference lists.
     * @param playerColor Player score to return
     * @return score
     */
    public int getScoreForPlayerColor(CheckerColor playerColor) {
        return playerColor == CheckerColor.BLACK ? this.blackCheckerPieceRefs.size() : this.redCheckerPieceRefs.size();
    }

    /**
     * Getter for current player color.
     * @return CheckerColor
     */
    public CheckerColor getCurrentPlayerColor() {
        // Have to do this because the gameboardkit does not support holding CheckerColor's
        // Used to derive the color of the player
        CheckerPiece currentPlayerCheckerPlaceholder = (CheckerPiece) this.getCurrentPlayerPiece();

        CheckerColor currentCheckerColor = currentPlayerCheckerPlaceholder.color;
        return currentCheckerColor;
    }

//    /**
//     * Checks to make sure the move is valid and then performs the move. It will set
//     * the checker to be placed into the new cell. If it was a single row move, it will
//     * change the player turn. Else, it will keep the same player and delete the checker
//     * that got jumped over.
//     * @param oldPosition
//     * @param newPosition
//     */
//    public void makeMoveWrapper(PosTuple oldPosition, PosTuple newPosition) {
//        this.makeMove(newPosition.row, newPosition.col);
//        this.moveCheck(oldPosition, newPosition);
//        this.handleMoveConditions(oldPosition, newPosition);
//        this.updateModelForMove(oldPosition, newPosition);
//    }

    /**
     * Goes into each checker piece and clears the valid moves stored
     * in the CheckerPiece object.
     */
    public void clearValidMovesForAllCheckerPieces() {
        this.clearValidMovesForCheckerPieces(this.blackCheckerPieceRefs);
        this.clearValidMovesForCheckerPieces(this.redCheckerPieceRefs);
    }

    /**
     * Gets the CheckerPiece object at the pos specified in PosTuple.
     * @return CheckerPiece at pos
     */
    public CheckerPiece getCheckerPieceAtPos(PosTuple pos) {
        CheckerPiece checkerToReturn = null;
        try {
            checkerToReturn = (CheckerPiece) this.returnPieceAt(pos.row, pos.col);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return checkerToReturn;
    }

    /**
     * Getter for this.jumping boolean.
     * @return true if jumping
     */
    public boolean isJumping() {
        return this.jumping;
    }

    /**
     * Checks if the checker can make the move to the position. This takes into account whether it is a king or not.
     * If the position is 2 rows away, it will check to see if there is an enemy checker in the intermediate cell.
     * @param checker CheckerPiece moving checker
     * @param pos PosTuple position that checker wants to move to
     * @return Boolean return true if it is a valid move
     */
    public boolean isValidMoveForChecker(CheckerPiece checker, PosTuple pos) {
        // if pos is only 1 row away, check if it is a valid move for the color
        if (Math.abs(checker.getPiecePos().row - pos.row) == 1) {
            // If the checker is jumping, don't allow single row moves.
            // The checker is only allowed to perform multiple jumps after
            // the first jump.
            if (this.jumping) {
                return false;
            }
            return this.isSingleRowMoveValid(checker, pos);
        }

        // If pos is 2 rows away, check if there is a jump available.
        // Can assume that it is two rows away if reached this point of flow.
        return this.isJumpMoveValid(checker, pos);
    }

    /**
     * Toggles the current player.
     */
    private void changeTurn() {
        this.switchCurrentPlayer();
    }

    /**
     * Checks to make sure that the old pos has a checker there and the new pos
     * does not have a checker there) and (make sure both positions are on the board).
     * This function is meant to be used before the actual move update.
     * @param oldPosition PosTuple pos where the checker currently is.
     * @param newPosition PosTuple pos where the checker will end up.
     * @return true if valid move
     */
    private boolean moveCheck(PosTuple oldPosition, PosTuple newPosition) {
        // (Check to make sure that the old pos has a checker there and the new pos
        // does not have a checker there) and (make sure both positions are on the board).
        if (gameBoard[oldPosition.row][oldPosition.col] == null ||
                gameBoard[newPosition.row][newPosition.col] != null ||
                !isValidPos(oldPosition) || !isValidPos(newPosition)) {
            throw new IllegalArgumentException("The position parameters in makeMove were not valid moves");
        }
        return true;
    }

    /**
     * Handles the different kind of conditions with each move. There are generally
     * two types of moves; a single row move and a jump move. If it is a single row
     * move, change the turn. If it is a jump move, remove the enemy checker that
     * got jumped over and set the jump rules for the next turn. The jump rules
     * will make sure that the continuous turn from jumping will be enforced.
     * @param oldPosition position before the move
     * @param newPosition position after the move
     */
    private void handleMoveConditions(PosTuple oldPosition, PosTuple newPosition) {
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

            // Set the jump rules to the checker jumping
            // The model hasn't been updated yet so it is still at old position
            this.setJumpRules((CheckerPiece) this.getCheckerPieceAtPos(oldPosition));
        }

        // Check for king state
        if (this.checkKingCondition(oldPosition, newPosition)) {
            // make the checker piece a king if in king state
            this.getCheckerPieceAtPos(oldPosition).makeKing();
        }
    }

    /**
     * Checks to see if the checker piece is in the right condition to become a king
     * @param newPosition position the checker is moving to
     * @return true if the piece is in king condition, false if
     */
    private boolean checkKingCondition(PosTuple oldPosition, PosTuple newPosition) {
        CheckerPiece checkerToCheck = this.getCheckerPieceAtPos(oldPosition);

        // if piece is already a king
        if (checkerToCheck.isKing) {
            return true;
        }

        // Return the boolean result of whether the king condition is true
        return (checkerToCheck.color == CheckerColor.BLACK && newPosition.row == 0) ||
                (checkerToCheck.color == CheckerColor.RED && newPosition.row == 7);
    }

    /**
     * Updates the gameboard model and the CheckerPiece model in order to reflect
     * the new situation after the move.
     * @param oldPosition position before the move
     * @param newPosition position after the move
     */
    private void updateModelForMove(PosTuple oldPosition, PosTuple newPosition) {
        // Update position value within CheckerPiece object
        CheckerPiece checkerToUpdate = this.getCheckerPieceAtPos(oldPosition);
        checkerToUpdate.updatePiecePos(newPosition.row, newPosition.col);

        // Update gameboard model to reflect move
        gameBoard[newPosition.row][newPosition.col] = checkerToUpdate;
        gameBoard[oldPosition.row][oldPosition.col] = null;
    }

    /**
     * Clears all of the valid moves for each CheckerPiece object in the
     * specified list.
     * @param checkerPieces list of CheckerPiece objects
     */
    private void clearValidMovesForCheckerPieces(ArrayList<CheckerPiece> checkerPieces) {
        for(CheckerPiece checker: checkerPieces) {
            checker.clearValidMoves();
        }
    }

    /**
     * Deletes the references to a checkerpiece given a row and col. This is
     * for a jump move where a piece needs to be removed from the game state.
     * @param row of piece to delete
     * @param col of piece to delete
     */
    private void deleteCheckerPiece(int row, int col) {
        CheckerPiece checkerToDelete = (CheckerPiece) this.gameBoard[row][col];
        CheckerColor enemyColor = checkerToDelete.color;

        if(enemyColor == CheckerColor.BLACK) {
            this.blackCheckerPieceRefs.remove(this.blackCheckerPieceRefs.indexOf(checkerToDelete));
        }
        else {
            this.redCheckerPieceRefs.remove(this.redCheckerPieceRefs.indexOf(checkerToDelete));
        }

        this.gameBoard[row][col] = null;
    }

    /**
     * Sets up the rules for the next turn so that the logic knows that the jumping
     * checker should be restricted.
     * @param checker CheckerPiece that is jumping
     */
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
        // and restrict it from making single moves
        if (this.jumping) {
            if (this.getValidMovesForChecker(this.jumpingCheckerPiece).size() != 0) {
                result.add(this.jumpingCheckerPiece);
            }
            // No more valid jumps available so change turn
            else {
                this.changeTurn();
                this.jumping = false;
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
        checker.setValidMoves(validMoves);
        return validMoves;
    }

    /**
     * Checks to see if the single row move is valid for the specified CheckerPiece
     * to the specified position.
     * @param checker CheckerPiece being checked for the move
     * @param pos PosTuple that represents the position being checked for a valid move
     * @return Returns true if the single row move is valid
     */
    private boolean isSingleRowMoveValid(CheckerPiece checker, PosTuple pos) {
        // Gets the row delta that would be valid for the color
        int rowDelta = checker.color == CheckerColor.BLACK ? checker.getPiecePos().row - 1 : checker.getPiecePos().row + 1;

        // if the pos being checked has a valid row delta or if the checker
        // is a king (if king, row deleta doesnt matter),
        // then check if cell is empty and if the col
        // delta is valid (single row move should only be moving 1 column away).
        if (pos.row == rowDelta || checker.isKing) {
            return this.gameBoard[pos.row][pos.col] == null && Math.abs(pos.col - checker.getPiecePos().col) == 1;
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

        // sets the row delta that would be valid for the color
        // sets the row that an enemy could possibly be on for the jump
        if (checker.color == CheckerColor.BLACK) {
            rowDelta = checker.getPiecePos().row - 2;
        }
        else {
            rowDelta = checker.getPiecePos().row + 2;
        }

        // sets the enemy row and col to the intermediate cell between the checker and final pos
        enemyCol = (checker.getPiecePos().col + pos.col) / 2;
        enemyRow = (checker.getPiecePos().row + pos.row) / 2;

        // if the pos being checked is a valid move for that color or the checker is a king,
        // check for enemy and check that the position
        // that the checker is jumping to is empty
        if (pos.row == rowDelta || checker.isKing) {
            return this.hasEnemy(checker, enemyRow, enemyCol) && this.gameBoard[pos.row][pos.col] == null;
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
        CheckerColor colorToCheckFor = checker.color == CheckerColor.BLACK ? CheckerColor.RED : CheckerColor.BLACK;
        CheckerPiece cellToCheck = (CheckerPiece) this.gameBoard[enemyRow][enemyCol];

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
            add(new PosTuple(checker.getPiecePos().row + 1, checker.getPiecePos().col - 1));
            add(new PosTuple(checker.getPiecePos().row + 1, checker.getPiecePos().col + 1));
            add(new PosTuple(checker.getPiecePos().row - 1, checker.getPiecePos().col - 1));
            add(new PosTuple(checker.getPiecePos().row - 1, checker.getPiecePos().col + 1));
            add(new PosTuple(checker.getPiecePos().row + 2, checker.getPiecePos().col - 2));
            add(new PosTuple(checker.getPiecePos().row + 2, checker.getPiecePos().col + 2));
            add(new PosTuple(checker.getPiecePos().row - 2, checker.getPiecePos().col - 2));
            add(new PosTuple(checker.getPiecePos().row - 2, checker.getPiecePos().col + 2));
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
     * Populates the gameBoard private variable with null values and the CheckerColor
     * enums based on the positions.
     */
    private void initializeGame() {
        //Sets placeholder checker pieces to mark the color of the player
        this.setPlayer1GamePiece(new CheckerPiece(-1,-1, CheckerColor.BLACK)); // Black always goes first
        this.setPlayer2GamePiece(new CheckerPiece(-1, -1, CheckerColor.RED));

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
     * Inserts CheckerColor.RED and CheckerColor.BLACK in the
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
                    CheckerPiece pieceToInsert = new CheckerPiece(i, j, CheckerColor.RED);
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
                    CheckerPiece pieceToInsert = new CheckerPiece(i, j, CheckerColor.BLACK);
                    gameBoard[i][j] = pieceToInsert;
                    this.blackCheckerPieceRefs.add(pieceToInsert);
                }
            }
        }
    }
}
