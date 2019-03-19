package othello.controllers;

import boardgamekit.BoardGameController;
import boardgamekit.players.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import othello.models.Othello;
import othello.utility.*;
import java.lang.Integer;

import java.io.IOException;
import java.util.ArrayList;


public class OthelloController extends BoardGameController {

    @FXML
    private ArrayList<ArrayList<StackPane>> gameboard;

    @FXML
    private Pane blackPlayerTurnPane;

    @FXML
    private Pane whitePlayerTurnPane;

    @FXML
    private Button resetButton;

//    @FXML
//    private Label player1Name;
//
//    @FXML
//    private Label player2Name;

//    @FXML
//    private Label infoLabel; --->     protected Label statusLabel;

//    @FXML
//    private Label blackScoreCard; --> player1Score

//    @FXML
//    private Label whiteScoreCard; --> player2Score


    //

    private Othello game;


    @Override
    public void initializeGameModel()
    {
        game = new Othello(this.player1,this.player2, 8, 8);
    }



    // initialize the game model, reset button, mouse click event on cells, set starting scores, and starts the turn
    public void initialize()
    {

        initializeGameModel();

        try {
            setResetButton();
            addMouseClickEvent();
            updateGameBoard();
            updateScore();
            startTurn();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setResetButton(){
        resetButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event2)
            {
                startNewGame();
            }
        });

    }


    // checks if the model has reached end game state first
    @Override
    public void startTurn()
    {

        if(game.endGame())
        {
            this.updateScore();
            game.endGameProcedure();
            if(game.getPlayer1Score() > game.getPlayer2Score())
            {
                this.updateStatus("Black Won!");
                // update player 1 score profile
            }
            else if(game.getPlayer1Score() < game.getPlayer2Score())
            {
                this.updateStatus("White Won!");
                //update player 2 score profile
            }
            else
                this.updateStatus("Tie Game!");
        }
        else {

            this.updateStatus("");
            if (!game.playerHasMoves(game.getCurrentTurn())) {

                game.setCurrentTurn(game.getCurrentTurn().getOppositeColor()); // switches turn if no moves available
                // add graphic for no moves / skipped turn
                this.updateStatus("No moves available, Turn Skipped!");
            }

            displayCurrentTurn();
        }
    }


    public void addMouseClickEvent()
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
                        int row = Integer.parseInt(cellPressed.substring(18,19));
                        int col = Integer.parseInt(cellPressed.substring(20,21));


                        makeMove(row,col);
                    }
                });
            }
        }
    }

    public boolean makeMove(int row, int col)
    {
        ArrayList<Integer[]> validMoves = ValidMoveFinder.getValidMoves(game.getCurrentTurn());


        for (Integer[] move : validMoves) {
            if (move[0] == row && move[1] == col) { // Move is verified as valid

                try {
                    game.makeMove(row, col);
                    this.updateGameBoard();
                    this.updateScore();

                    startTurn();
                    return true;
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

            }
        }

        // If move is not valid
        this.updateStatus("Invalid Move");
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
                this.gameboard.get(i).get(j).getChildren().clear();
                if(updatedBoard[i][j] == null)
                {
                    continue;
                }
                else
                {
                    if(updatedBoard[i][j].toString().equals("W"))
                    {

//                        System.out.println("found White");
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
        setPlayer1Score(Integer.toString(game.getPlayer1Score()));
        setPlayer2Score(Integer.toString(game.getPlayer2Score()));
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

    public void displayCurrentTurn() {
        // Clear the current turn arrow from previous turn
        this.blackPlayerTurnPane.getChildren().clear();
        this.whitePlayerTurnPane.getChildren().clear();

//        ArrayList<Integer[]> moves = ValidMoveFinder.getValidMoves(game.getCurrentTurn());
//        System.out.println("Available Moves Found");
//            System.out.println("Valid Moves:");
//            for (Integer[] move : moves) {
//                System.out.println(move[0] +"  "+move[1]);
//            }

        try {
            AnchorPane currentTurnArrow = FXMLLoader.load(getClass().getResource("/views/othello/CurrentTurnArrow.fxml"));
            StackPane.setAlignment(currentTurnArrow, Pos.CENTER);

            if (this.game.getCurrentTurn() == OthelloPlayer.BLACK) {
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

    @Override
    public void startNewGame()
    {
        try {
            this.initializeGameModel();
            this.updateStatus("New Game");
            this.updateScore();
            this.updateGameBoard();
            this.startTurn();
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
