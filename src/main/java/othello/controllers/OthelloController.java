package othello.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OthelloController{

    @FXML
    private ArrayList<ArrayList<StackPane>> gameboard;



    @FXML
    public void initialize() {
        try{
            initializeBlackStartingPieces();
            initializeWhiteStartingPieces();

        }

        catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void initializeBlackStartingPieces()throws IOException
    {
        StackPane blackStartingPiece1 = gameboard.get(4).get(3);
        StackPane blackStartingPiece2 = gameboard.get(3).get(4);

        StackPane[] pieces = {blackStartingPiece1,blackStartingPiece2};


        for(int i =0; i < 2; ++i) {
            AnchorPane blackOthelloPiece = FXMLLoader.load(getClass().getResource("/views/othello/BlackOthelloPiece.fxml"));

            StackPane.setAlignment(blackOthelloPiece, Pos.CENTER);


            pieces[i].getChildren().add(blackOthelloPiece);

        }

        // Set user data which stores pos and event handler refs
//        GameboardNodeInfo cellInfo = (GameboardNodeInfo) cell.getUserData();
//        GameboardNodeInfo checkerPieceInfo = new GameboardNodeInfo(cellInfo.boardPosition);
//        redCheckerPiece.setUserData(checkerPieceInfo);

    }

    private void initializeWhiteStartingPieces() throws IOException
    {
        StackPane whiteStartingPiece1 = gameboard.get(3).get(3);
        StackPane whiteStartingPiece2 = gameboard.get(4).get(4);

        StackPane[] pieces = {whiteStartingPiece1,whiteStartingPiece2};


        for(int i =0; i < 2; ++i) {

            AnchorPane whiteOthelloPiece = FXMLLoader.load(getClass().getResource("/views/othello/WhiteOthelloPiece.fxml"));

            StackPane.setAlignment(whiteOthelloPiece, Pos.CENTER);


            pieces[i].getChildren().add(whiteOthelloPiece);

        }

        // still need to add changes to the model here

    }

}
