package memory.controllers;

import memory.models.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class MemoryController {


    @FXML
    private ImageView iv1;
    @FXML
    private Button button1;

    @FXML
    private ImageView iv2;
    @FXML
    private Button button2;

    @FXML
    private ImageView iv3;
    @FXML
    private Button button3;

    @FXML
    private ImageView iv4;
    @FXML
    private Button button4;

    public void cardClicked1(){
        iv1.setVisible(true);
        button1.setVisible(false);
        }

    public void cardClicked2(){
        iv2.setVisible(true);
        button2.setVisible(false);
    }

    public void cardClicked3(){
        iv3.setVisible(true);
        button3.setVisible(false);
    }

    public void cardClicked4(){
        iv4.setVisible(true);
        button4.setVisible(false);
    }


    public Label statusLabel;
    private Memory game = new Memory();

    @FXML
    protected void initialize() {
        statusLabel.setText("Turn: Player One");
    }

    private void cardFlipped(int cardNum) {

        int moveNum;
        //label.setText(currentPlayer.toString());

        if (game.allCardsFlipped()) {
            //GAME IS FINISHED
            statusLabel.setText("Game Over: Player " + game.whoWon() + " wins!");
        } else {
            //GAME UNFINISHED
            moveNum = game.makeMove(cardNum);
            switch(moveNum){
                case 1: //CASE 1: SHOW IMAGE
                case 2: //CASE 2: SHOW IMAGE FOR A SMALL AMOUNT OF TIME THEN FLIP BOTH CARDS BACK
                case 3: //CASE 3: KEEP BOTH IMAGES SHOWING
            }
            statusLabel.setText("Turn: Player " + game.getPlayerString());
        }
    }

//    public void startNewGame(ActionEvent actionEvent) {
//        game = new Memory();
//        statusLabel.setText("Turn: Player One");
//
//    }



}
