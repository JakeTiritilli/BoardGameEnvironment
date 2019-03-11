package checkers.controllers;

import checkers.models.Checkers;
import checkers.utility.CheckerPiece;
import checkers.utility.PosTuple;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;

public class CheckerboardController {
    private Checkers game = new Checkers();
    private ArrayList<AnchorPane> blackSideActiveCheckerPieces = new ArrayList(); // container of references to active black side checker pieces
    private ArrayList<AnchorPane> redSideActiveCheckerPieces = new ArrayList(); // container of references to active red side checker pieces
    private ArrayList<StackPane> markedValidMoveCells = new ArrayList();
    private AnchorPane currentlySelectedCheckerPiece = null;

    @FXML
    ArrayList<ArrayList<StackPane>> gameboard; // 2D array that represents the gameboard
    @FXML
    ArrayList<StackPane> redSideCellList; // References to the red side black cells
    @FXML
    ArrayList<StackPane> blackSideCellList; // References to the black side black cells

    @FXML
    public void initialize() {
        this.initializeGamePieces();
        this.displayMovableCheckerPieces();
    }

    /**
     * Places a yellow circle on all of the valid moves for each movable
     * checker piece. The valid moves are stored in the CheckerPiece object.
     */
    public void displayMovableCheckerPieces() {
        ArrayList<CheckerPiece> movableCheckerPieces = this.game.getMovableCheckerPiecesForActivePlayer();
        for(CheckerPiece checker : movableCheckerPieces) {
            this.addCheckerClickListener(checker);
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
            }
        };
        return eventHandler;
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

            // Add to list of marked cells to make clearing the mark easier
            this.markedValidMoveCells.add(validMoveCell);
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
            this.blackSideActiveCheckerPieces.add(blackCheckerPiece);
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
            this.redSideActiveCheckerPieces.add(redCheckerPiece);
        }
    }

    /**
     * Clears the cells that have the valid move indicator.
     */
    private void clearMarkedValidMoveCells() {
        for (StackPane validMoveCell : this.markedValidMoveCells) {
            validMoveCell.getChildren().clear();
        }
        this.markedValidMoveCells = new ArrayList<>();
    }
}
