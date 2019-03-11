package checkers.controllers;

import checkers.models.Checkers;
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
    ArrayList<StackPane> blackCellList; // reference to all black cells
    @FXML
    ArrayList<StackPane> redCellList; // reference to all red cells
    @FXML
    ArrayList<StackPane> redSideCellList; // reference to red side cells (only playable cells)
    @FXML
    ArrayList<StackPane> blackSideCellList; // reference to black side cells (only playable cells)

    @FXML
    public void initialize() {
        this.initializeGamePieces();
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
