package othello.controllers;

import boardgamekit.players.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import othello.models.Othello;
import othello.utility.*;
import java.lang.Integer;

import java.io.IOException;
import java.util.ArrayList;


public class OthelloController {

    @FXML
    private ArrayList<ArrayList<StackPane>> gameboard;

    @FXML
    private Pane board;


    @FXML
    private Pane blackPlayerTurnPane;

    @FXML
    private Pane whitePlayerTurnPane;

    @FXML
    private Label infoLabel;

    @FXML
    private Label blackScoreCard;

    @FXML
    private Label whiteScoreCard;

    private Othello game;


    @FXML
    public void initialize() {

        game = new Othello(Player.createDefault(""), Player.createDefault(""), 8, 8);

        try {
            initializeBoard();
            displayCurrentTurn();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void initializeBoard() throws IOException {
        initializeBlackStartingPieces();
        initializeWhiteStartingPieces();
        addMouseClickEvent();
        updateScore();
        startTurn();

    }

    private void startTurn()
    {
        infoLabel.setText("");
        if(!game.playerHasMoves(game.getCurrentPlayer()))
        {

            game.setCurrentPlayer(game.getCurrentPlayer().getOppositeColor()); // switches turn if no moves available
            // add graphic for no moves / skipped turn
            infoLabel.setText("No moves available, Turn Skipped!");
        }

        displayCurrentTurn();
    }

    private void addMouseClickEvent()
    {
        for(int i =0; i < gameboard.size(); ++i)
        {
            for(int j = 0; j < gameboard.get(i).size(); ++j)
            {
                gameboard.get(i).get(j).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
//                        System.out.println("mouse click detected! " + mouseEvent.getSource());

                        String cellPressed = mouseEvent.getSource().toString();
                        System.out.println(cellPressed);
                        int row = Integer.parseInt(cellPressed.substring(18,19));
                        int col = Integer.parseInt(cellPressed.substring(20,21));


                        System.out.println(makeMove(row,col));
                    }
                });
            }
        }
    }

    private boolean makeMove(int row, int col)
    {
        ArrayList<Integer[]> validMoves = ValidMoveFinder.getValidMoves(game.getCurrentPlayer());

        for (Integer[] move : validMoves) {
            if (move[0] == row && move[1] == col) { // Move is verified as valid

                    try {
                        game.makeMove(row, col);
                        this.updateGameBoard();
                        this.updateScore();
                        // update score

                        startTurn();
                        return true;
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

            }
        }

        game.setCurrentPlayer(game.getCurrentPlayer().getOppositeColor());
        startTurn();
        return false;



    }


    private void updateGameBoard() throws IOException
    {

        String pieceFile = "";
        OthelloPiece[][] updatedBoard = game.getBoard();
        for(int i = 0; i <updatedBoard.length; ++i)
        {
            for(int j = 0; j < updatedBoard[i].length; ++j)
            {
                gameboard.get(i).get(j).getChildren().clear();
                if(updatedBoard[i][j] == null)
                {
                   continue;
                }
                else
                {
                    if(updatedBoard[i][j].toString().equals(" W "))
                    {

                        System.out.println("found White");
                        pieceFile = "/views/othello/WhiteOthelloPiece.fxml";
                        AnchorPane whiteOthelloPiece = FXMLLoader.load(getClass().getResource(pieceFile));

                        StackPane.setAlignment(whiteOthelloPiece, Pos.CENTER);


                       gameboard.get(i).get(j).getChildren().add(whiteOthelloPiece);
                    }
                    else
                    {
                        pieceFile = "/views/othello/BlackOthelloPiece.fxml";
                        AnchorPane BlackOthelloPiece = FXMLLoader.load(getClass().getResource(pieceFile));

                        StackPane.setAlignment(BlackOthelloPiece, Pos.CENTER);


                        gameboard.get(i).get(j).getChildren().add(BlackOthelloPiece);
                    }
                }
            }
        }

        // start next turn

    }

    private void updateScore()
    {

        blackScoreCard.setText(Integer.toString(game.getP1Score()));
        whiteScoreCard.setText(Integer.toString(game.getP2Score()));
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

    private void displayCurrentTurn() {
        // Clear the current turn arrow from previous turn
        this.blackPlayerTurnPane.getChildren().clear();
        this.whitePlayerTurnPane.getChildren().clear();

        ArrayList<Integer[]> moves = ValidMoveFinder.getValidMoves(game.getCurrentPlayer());
        System.out.println("Available Moves Found");
            System.out.println("Valid Moves:");
            for (Integer[] move : moves) {
                System.out.println(move[0] +"  "+move[1]);
            }

        try {
            AnchorPane currentTurnArrow = FXMLLoader.load(getClass().getResource("/views/othello/CurrentTurnArrow.fxml"));
            StackPane.setAlignment(currentTurnArrow, Pos.CENTER);

            if (this.game.getCurrentPlayer() == OthelloPlayer.BLACK) {
                this.blackPlayerTurnPane.getChildren().add(currentTurnArrow);
            }
            else {
                this.whitePlayerTurnPane.getChildren().add(currentTurnArrow);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
