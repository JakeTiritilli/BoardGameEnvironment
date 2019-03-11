package checkers.controllers;

import checkers.models.Checkers;
import checkers.utility.CheckerPiece;
import checkers.utility.PosTuple;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;

public class CheckerboardController {
    private Checkers game = new Checkers();
    private ArrayList<AnchorPane> blackSideActiveCheckerPieces = new ArrayList(); // container of references to active black side checker pieces
    private ArrayList<AnchorPane> redSideActiveCheckerPieces = new ArrayList(); // container of references to active red side checker pieces

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

    public void displayMovableCheckerPieces() {
        ArrayList<CheckerPiece> movableCheckerPieces = this.game.getMovableCheckerPiecesForActivePlayer();
        for(CheckerPiece checker : movableCheckerPieces) {
            for(PosTuple validMove : checker.validMoves) {
                this.displayValidMove(validMove);
            }
        }
    }

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
            cell.getChildren().add(blackCheckerPiece);
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
            cell.getChildren().add(redCheckerPiece);
        }
    }
}
