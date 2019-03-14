package checkers.controllers;

import checkers.models.Checkers;
import checkers.utility.CheckerPiece;
import checkers.utility.CheckerPlayer;
import checkers.utility.GameboardNodeInfo;
import checkers.utility.PosTuple;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;

public class CheckerboardController {
    private Checkers game = new Checkers();
    private AnchorPane currentlySelectedCheckerPiece = null;

    // These array lists are stored in order to remove the event handlers each turn
    private ArrayList<StackPane> validMoveCellsWithEventHandlers = new ArrayList<>();
    private ArrayList<AnchorPane> checkerPiecesWithEventHandlers = new ArrayList<>();

    @FXML
    ArrayList<ArrayList<StackPane>> gameboard; // 2D array that represents the gameboard
    @FXML
    ArrayList<StackPane> redSideCellList; // References to the red side black cells
    @FXML
    ArrayList<StackPane> blackSideCellList; // References to the black side black cells
    @FXML
    Pane blackPlayerTurnPane;
    @FXML
    Pane redPlayerTurnPane;
    @FXML
    Label blackPlayerScoreLabel;
    @FXML
    Label redPlayerScoreLabel;

    /**
     * Initializes the user data for gameboard which places a GameboardNodeInfo object
     * into each cell Node. This is used in order to derive game data so that it can
     * be passed to the model. It then will initialize the game pieces on the board and then
     * display valid moves for the movable checkers.
     */
    @FXML
    public void initialize() {
        this.initializeUserDataForGameboard();
        this.initializeGamePieces();
        this.updateDisplayedScore();
        this.startTurn();
    }

    /**
     * Clears the board of the previous turn artifacts and sets up the board to show
     * the new valid moves for the current turn.
     */
    public void startTurn() {
        // Cleans the board and sets it up for the next turn/move
        this.clearEventHandlersAndIndicators();
        this.displayCurrentTurn();
        displayMovableCheckerPieces();
    }

    /**
     * Places the arrow above the player that is currently going
     */
    private void displayCurrentTurn() {
        // Clear the current turn arrow from previous turn
        this.blackPlayerTurnPane.getChildren().clear();
        this.redPlayerTurnPane.getChildren().clear();

        try {
            AnchorPane currentTurnArrow = FXMLLoader.load(getClass().getResource("/views/checkers/CurrentTurnArrow.fxml"));
            StackPane.setAlignment(currentTurnArrow, Pos.CENTER);

            if (this.game.getCurrentPlayer() == CheckerPlayer.BLACK) {
                this.blackPlayerTurnPane.getChildren().add(currentTurnArrow);
            }
            else {
                this.redPlayerTurnPane.getChildren().add(currentTurnArrow);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Places a yellow circle on all of the valid moves for each movable
     * checker piece. The valid moves are stored in the CheckerPiece object.
     */
    private void displayMovableCheckerPieces() {
        ArrayList<CheckerPiece> movableCheckerPieces = this.game.getMovableCheckerPiecesForActivePlayer();

        // No valid turns so restart.
        // The turn changes internally within the model based on conditions
        if (movableCheckerPieces.size() == 0) {
            this.displayMovableCheckerPieces();
        }

        for(CheckerPiece checker : movableCheckerPieces) {

            // Add the click listeners for the movable checkers
            this.addCheckerClickListener(checker);

            // Add the indicators and click listeners for the valid moves
            for(PosTuple validMove : checker.validMoves) {
                this.displayValidMove(validMove);
            }
        }
    }

    /**
     * Adds the click listener to the checker in order to make it selectable.
     * @param checker Holds the position of the checkerpiece on the GUI board.
     */
    private void addCheckerClickListener(CheckerPiece checker) {
        StackPane targetCell = this.gameboard.get(checker.position.row).get(checker.position.col);

        // Get the checker piece node on the cell
        AnchorPane targetCheckerPiece = (AnchorPane) targetCell.getChildren().get(0);

        // Create handle for movable checker piece and attach the handler
        EventHandler<MouseEvent> handler = this.createCheckerPieceEventHandler(targetCheckerPiece, this);
        targetCheckerPiece.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

        // Saves the ref to the event handler in the target checker piece.
        // This is so that we can clear the event handler later on.
        // To clear an event handler, it requires a ref to the event handler.
        GameboardNodeInfo checkerPieceInfo = (GameboardNodeInfo) targetCheckerPiece.getUserData();
        checkerPieceInfo.clickEventHandler = handler;

        // Add refs of checkers with event handlers so that we can clear them later easily
        this.checkerPiecesWithEventHandlers.add(targetCheckerPiece);
    }

    /**
     * Switches the currently selected checker piece with the latest one.
     * This will take away the selection indicator on the previous checker
     * and put it on the new one.
     * @param latestChecker The latest selected checker piece.
     */
    private void switchSelectedCheckerPiece(AnchorPane latestChecker) {
        if(this.currentlySelectedCheckerPiece == null) {
            this.currentlySelectedCheckerPiece = latestChecker;
            return;
        }
        Circle innerCircle = (Circle) this.currentlySelectedCheckerPiece.getChildren().get(1);
        innerCircle.setFill(Color.rgb(255,255,255,0));
        this.currentlySelectedCheckerPiece = latestChecker;
    }

    /**
     * Places a move indicator for the position indicated. The move indicator is a yellow circle which
     * is supposed to indicate that the active player can make a move to that position with one of the
     * checker pieces.
     * @param validMove PosTuple containing the coordinates of the cell to be marked
     */
    private void displayValidMove(PosTuple validMove) {
        StackPane validMoveCell = this.gameboard.get(validMove.row).get(validMove.col);
        AnchorPane validMoveCircle = null;

        // Load valid move circle indicator
        try {
            validMoveCircle = FXMLLoader.load(getClass().getResource("/views/checkers/ValidMoveCircle.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        StackPane.setAlignment(validMoveCircle, Pos.CENTER);

        // Add the indicator if there isn't one there already
        // This is to prevent overlapping indicators causing GUI to look weird
        if(validMoveCell.getChildren().size() == 0) {
            validMoveCell.getChildren().add(validMoveCircle);

            this.addValidMoveClickEventHandlers(validMoveCell);
        }
    }

    /**
     * Adds click handlers to the cells that are valid moves for the movable checkers.
     * @param validMoveCell
     */
    private void addValidMoveClickEventHandlers(StackPane validMoveCell) {
        // Create and add event listener for valid move cell.
        // This is for if a checker is selected and the valid move cell is clicked
        // then perform the move.
        EventHandler<MouseEvent> handler = this.createValidMoveClickEventHandler(validMoveCell, this);
        validMoveCell.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

        // Saves the ref to the event handler in the valid move cell.
        // This is so that we can clear the event handler later on.
        // To clear an event handler, it requires a ref to the event handler.
        GameboardNodeInfo cellInfo = (GameboardNodeInfo) validMoveCell.getUserData();
        cellInfo.clickEventHandler = handler;

        // Add to list of marked cells to make clearing the mark easier
        this.validMoveCellsWithEventHandlers.add(validMoveCell);
    }

    /**
     * Creates a click handler that will initiate the makeMove method on a valid
     * click. A valid click would be that a checker is selected and the checker is
     * valid to move to that cell.
     * @param validMoveCell
     * @param ref
     * @return
     */
    private EventHandler<MouseEvent> createValidMoveClickEventHandler(StackPane validMoveCell,
                                                                      CheckerboardController ref) {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameboardNodeInfo validMoveCellInfo = (GameboardNodeInfo) validMoveCell.getUserData();
                PosTuple validMoveCellPos = validMoveCellInfo.boardPosition;

                // If there is a checker selected and the cell clicked is a valid move for
                // that checker, then perform the move.
                if (ref.currentlySelectedCheckerPiece != null &&
                        ref.isValidMoveForCurrentlySelectedChecker(validMoveCellPos)) {
                    ref.makeMove(validMoveCell);
                }
                event.consume();
            }
        };
        return eventHandler;
    }

    /**
     * Checks to make sure that the currently selected checker can make a valid move to
     * the specified cell.
     * @param validMoveCellPos pos of the cell the checker is trying to move to
     * @return true if valid move
     */
    private boolean isValidMoveForCurrentlySelectedChecker(PosTuple validMoveCellPos) {
        GameboardNodeInfo currentlySelectedCheckerPieceInfo =
                (GameboardNodeInfo) this.currentlySelectedCheckerPiece.getUserData();
        PosTuple checkerPiecePos = currentlySelectedCheckerPieceInfo.boardPosition;
        return this.game.isValidMoveForChecker(this.game.getCheckerPieceAtPos(checkerPiecePos), validMoveCellPos);
    }

    /**
     * Creates an event handler for the movable checker pieces. The handler will fill
     * the checker piece in with yellow to show the user that it is selected. It will
     * also switch the currently selected checker piece in the controller.
     * @param targetCheckerPiece
     * @param ref
     * @return
     */
    private EventHandler<MouseEvent> createCheckerPieceEventHandler(AnchorPane targetCheckerPiece, CheckerboardController ref) {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Circle innerCircle = (Circle) targetCheckerPiece.getChildren().get(1);
                innerCircle.setFill(Color.rgb(255,255,0, 1));
                ref.switchSelectedCheckerPiece(targetCheckerPiece);
                event.consume();
            }
        };
        return eventHandler;
    }

    /**
     * Clears the cell where the checker originally was and moves the checker to the new
     * cell. It then communicates the change to the model.
     * @param validMoveCell cell that the checker is moving to.
     */
    private void makeMove(StackPane validMoveCell) {
        // Clear the cell that the currently selected checker piece is in
        StackPane currentCell = (StackPane) this.currentlySelectedCheckerPiece.getParent();
        currentCell.getChildren().clear();

        // Extract cell info
        GameboardNodeInfo oldCellInfo = (GameboardNodeInfo) currentCell.getUserData();
        GameboardNodeInfo newCellInfo = (GameboardNodeInfo) validMoveCell.getUserData();
        PosTuple oldPosition = oldCellInfo.boardPosition;
        PosTuple newPosition = newCellInfo.boardPosition;

        // Move the currently selected checker piece to the new cell and update checker position
        validMoveCell.getChildren().add(this.currentlySelectedCheckerPiece);
        GameboardNodeInfo checkerInfo = (GameboardNodeInfo) this.currentlySelectedCheckerPiece.getUserData();
        checkerInfo.boardPosition = newPosition;

        // Communicate changes to model
        this.updateModelForMove(oldPosition, newPosition);

        // Update displayed score
        this.updateDisplayedScore();

        // Delete enemy if jumped
        this.handleJumpCondition(oldPosition, newPosition);

        // Restart the turn loop
        this.startTurn();
    }

    /**
     * Updates the displayed score for each player. The score represents how many
     * pieces they have left on the board.
     */
    private void updateDisplayedScore() {
        this.blackPlayerScoreLabel.setText(Integer.toString(this.game.getScoreForPlayer(CheckerPlayer.BLACK)));
        this.redPlayerScoreLabel.setText(Integer.toString(this.game.getScoreForPlayer(CheckerPlayer.RED)));
    }

    /**
     * Updates the model for the move made. Also clears the valid moves within the model
     * to set up the model for the next turn.
     * @param oldPosition
     * @param newPosition
     */
    private void updateModelForMove(PosTuple oldPosition, PosTuple newPosition) {
        this.game.makeMove(oldPosition, newPosition);
        this.game.clearValidMovesForAllCheckerPieces();
    }

    /**
     * Handles the condition if a checker is currently jumping. This will be called
     * after the move is made so if the game is in jumping state, a checker needs to be
     * deleted.
     * @param oldPosition position before the move
     * @param newPosition position after the move
     */
    private void handleJumpCondition(PosTuple oldPosition, PosTuple newPosition) {
        // If game.jumping == true, then make sure to delete the enemy
        if (this.game.isJumping()) {
            int enemyRow = (oldPosition.row + newPosition.row) / 2;
            int enemyCol = (oldPosition.col + newPosition.col) / 2;
            getCellAtPos(enemyRow, enemyCol).getChildren().clear();
        }
    }

    /**
     * Returns the cell at the pos
     * @param row of target cell
     * @param col of target cell
     * @return StackPane cell ref
     */
    private StackPane getCellAtPos(int row, int col) {
        return this.gameboard.get(row).get(col);
    }

    /**
     * Clears event handlers and GUI artifacts from the board.
     */
    private void clearEventHandlersAndIndicators() {
        // Only execute when needed
        if (this.currentlySelectedCheckerPiece != null) {
            this.clearCurrentlySelectedCheckerPiece();
            this.clearValidMoveIndicators();
            this.clearSelectableCheckerPiecesEventHandlers();
            this.clearValidMoveCellsEventHandlers();
        }
    }

    /**
     * Clears the yellow circles that indicate a valid move can be made.
     */
    private void clearValidMoveIndicators() {
        for (StackPane validMoveCell : this.validMoveCellsWithEventHandlers) {
            validMoveCell.getChildren().remove(0);
        }
    }

    /**
     * Clears the indicator on the GUI of the selected checker piece and
     * sets the value of currentlySelectedCheckerPiece to null.
     */
    private void clearCurrentlySelectedCheckerPiece() {
        Circle innerCircle = (Circle) this.currentlySelectedCheckerPiece.getChildren().get(1);
        innerCircle.setFill(Color.rgb(255,255,255,0));
        this.currentlySelectedCheckerPiece = null;
    }

    /**
     * Clears the cells that have the valid move indicator.
     */
    private void clearValidMoveCellsEventHandlers() {
        for (StackPane validMoveCell : this.validMoveCellsWithEventHandlers) {
            this.clearEventHandlerForNode(validMoveCell);
        }
        this.validMoveCellsWithEventHandlers = new ArrayList<>();
    }

    /**
     * Clears the event handlers on the movable checker pieces.
     */
    private void clearSelectableCheckerPiecesEventHandlers() {
        for (AnchorPane checker : this.checkerPiecesWithEventHandlers) {
            this.clearEventHandlerForNode(checker);
        }
        this.checkerPiecesWithEventHandlers = new ArrayList<>();
    }

    /**
     * Clears the event handlers on the given Pane node.
     * @param gameboardNode Pane object which can represent a cell or checker
     */
    private void clearEventHandlerForNode(Pane gameboardNode) {
        GameboardNodeInfo nodeInfo = (GameboardNodeInfo) gameboardNode.getUserData();
        EventHandler<MouseEvent> handlerRef = nodeInfo.clearClickEventHanlder();
        gameboardNode.removeEventFilter(MouseEvent.MOUSE_CLICKED, handlerRef);
    }

    /**
     * Attaches a PosTuple to each cell on the gameboard in order to make it
     * easier to derive position. This is used in order to communicate info
     * to the model.
     */
    private void initializeUserDataForGameboard() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                GameboardNodeInfo info = new GameboardNodeInfo(new PosTuple(row,col));
                this.gameboard.get(row).get(col).setUserData(info);
            }
        }
    }

    /**
     * Places the GUI Gamepieces into their correct cells.
     */
    private void initializeGamePieces() {
        try {
            initializeBlackSideGamePieces();
            initializeRedSideGamePieces();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Places a BLACK checker piece into each cell reference from blackSideCellList.
     * @throws IOException from FXMLLoader
     */
    private void initializeBlackSideGamePieces() throws IOException {
        for(StackPane cell: blackSideCellList) {
            AnchorPane blackCheckerPiece = FXMLLoader.load(getClass().getResource("/views/checkers/BlackCheckerPiece.fxml"));
            StackPane.setAlignment(blackCheckerPiece, Pos.CENTER);

            // Add the checker piece to the GUI cell and add it to the active black checker
            // piece list.
            cell.getChildren().add(blackCheckerPiece);

            // Set user data which stores pos and event handler refs
            GameboardNodeInfo cellInfo = (GameboardNodeInfo) cell.getUserData();
            GameboardNodeInfo checkerPieceInfo = new GameboardNodeInfo(cellInfo.boardPosition);
            blackCheckerPiece.setUserData(checkerPieceInfo);
        }
    }

    /**
     * Places a RED checker piece into each cell reference from redSideCellList.
     * @throws IOException from FXMLLoader
     */
    private void initializeRedSideGamePieces() throws IOException {
        for(StackPane cell: redSideCellList) {
            AnchorPane redCheckerPiece = FXMLLoader.load(getClass().getResource("/views/checkers/RedCheckerPiece.fxml"));
            StackPane.setAlignment(redCheckerPiece, Pos.CENTER);

            // Add the checker piece to the GUI cell and add it to the active black checker
            // piece list.
            cell.getChildren().add(redCheckerPiece);

            // Set user data which stores pos and event handler refs
            GameboardNodeInfo cellInfo = (GameboardNodeInfo) cell.getUserData();
            GameboardNodeInfo checkerPieceInfo = new GameboardNodeInfo(cellInfo.boardPosition);
            redCheckerPiece.setUserData(checkerPieceInfo);
        }
    }
}
