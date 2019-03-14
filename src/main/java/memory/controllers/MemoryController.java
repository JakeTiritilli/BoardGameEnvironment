package memory.controllers;

/**
 * Represents a controller for and contains general functionality for playing the
 * game of Memory.
 *
 * @author Kaitlyn Fong
 */

import memory.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button ;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class MemoryController {

    @FXML
    public Label statusLabel;

    @FXML
    public Label playeronescore;

    @FXML
    public Label playertwoscore;

    @FXML
    private ImageView iv1;
    @FXML
    private Button  button1;

    @FXML
    private ImageView iv2;
    @FXML
    private Button  button2;

    @FXML
    private ImageView iv3;
    @FXML
    private Button  button3;

    @FXML
    private ImageView iv4;
    @FXML
    private Button  button4;

    @FXML
    private ImageView iv5;
    @FXML
    private Button  button5;

    @FXML
    private ImageView iv6;
    @FXML
    private Button  button6;

    @FXML
    private void handleCardOne() {
        cardFlipped(1);
    }
    public void cardShow1(){
        iv1.setVisible(true);
        button1.setVisible(false);
        }
    public void cardHide1(){
        iv1.setVisible(false);
        button1.setVisible(true);
    }

    @FXML
    private void handleCardTwo() {
        cardFlipped(2);
    }
    public void cardShow2(){
        iv2.setVisible(true);
        button2.setVisible(false);
    }
    public void cardHide2(){
        iv2.setVisible(false);
        button2.setVisible(true);
    }

    @FXML
    private void handleCardThree() {
        cardFlipped(3);
    }
    public void cardShow3(){
        iv3.setVisible(true);
        button3.setVisible(false);
    }
    public void cardHide3(){
        iv3.setVisible(false);
        button3.setVisible(true);
    }

    @FXML
    private void handleCardFour() {
        cardFlipped(4);
    }
    public void cardShow4(){
        iv4.setVisible(true);
        button4.setVisible(false);
    }
    public void cardHide4(){
        iv4.setVisible(false);
        button4.setVisible(true);
    }

    @FXML
    private void handleCardFive() {
        cardFlipped(5);
    }
    public void cardShow5(){
        iv5.setVisible(true);
        button5.setVisible(false);
    }
    public void cardHide5(){
        iv5.setVisible(false);
        button5.setVisible(true);
    }

    @FXML
    private void handleCardSix() {
        cardFlipped(6);
    }
    public void cardShow6(){
        iv6.setVisible(true);
        button6.setVisible(false);
    }
    public void cardHide6(){
        iv6.setVisible(false);
        button6.setVisible(true);
    }


    private Memory game = new Memory();


    public void cardFlipped(int cardNum) {

        int moveNum;

        if (!game.allCardsFlipped()) {
            //GAME UNFINISHED
            moveNum = game.makeMove(cardNum);
            switch(moveNum){
                case 1: //CASE 1: SHOW IMAGE AND KEEP SHOWN
                    playeronescore.setText("Score: " + game.playerOneScore);
                    playertwoscore.setText("Score: " + game.playerTwoScore);
                    switch(cardNum){
                        case 1: cardShow1(); break;
                        case 2: cardShow2(); break;
                        case 3: cardShow3(); break;
                        case 4: cardShow4(); break;
                        case 5: cardShow5(); break;
                        case 6: cardShow6(); break;
                    } break;
                case 2: //CASE 2: SHOW IMAGE FOR A SMALL AMOUNT OF TIME THEN FLIP BOTH CARDS BACK
//                    playeronescore.setText("Score: " + game.playerOneScore);
//                    playertwoscore.setText("Score: " + game.playerTwoScore);
                    switch(cardNum){
                        case 1: cardShow1(); break;
                        case 2: cardShow2(); break;
                        case 3: cardShow3(); break;
                        case 4: cardShow4(); break;
                        case 5: cardShow5(); break;
                        case 6: cardShow6(); break;
                    }
                    try {
                        Thread.sleep(4000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }

                    //hide first card
                    switch(game.currentPlayerPick[0]){
                        case 1: cardHide1(); break;
                        case 2: cardHide2(); break;
                        case 3: cardHide3(); break;
                        case 4: cardHide4(); break;
                        case 5: cardHide5(); break;
                        case 6: cardHide6(); break;
                    }
                    switch(cardNum){
                        case 1: cardHide1(); break;
                        case 2: cardHide2(); break;
                        case 3: cardHide3(); break;
                        case 4: cardHide4(); break;
                        case 5: cardHide5(); break;
                        case 6: cardHide6(); break;
                    }
                    game.changePlayer();
                    break;
                case 3: //CASE 3: KEEP BOTH IMAGES SHOWING
                    playeronescore.setText("Score: " + game.playerOneScore);
                    playertwoscore.setText("Score: " + game.playerTwoScore);
                    switch(cardNum){
                        case 1: cardShow1(); break;
                        case 2: cardShow2(); break;
                        case 3: cardShow3(); break;
                        case 4: cardShow4(); break;
                        case 5: cardShow5(); break;
                        case 6: cardShow6(); break;
                    }
                    if (game.allCardsFlipped()) {
                        statusLabel.setText("Game Over: Player " + game.whoWon() + " wins!");
                        return;
                    }
                    else
                        game.changePlayer();
                    break;

            }
            statusLabel.setText("Turn: Player " + game.getPlayerString());
        }
         else {
        //GAME IS FINISHED
        //statusLabel.setText("Game Over: Player " + game.whoWon() + " wins!");
        return;
         }
         }

//    public void startNewGame(ActionEvent actionEvent) {
//        game = new Memory();
//        statusLabel.setText("Turn: Player One");
//
//    }



}
