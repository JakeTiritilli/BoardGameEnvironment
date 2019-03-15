package memory.controllers;

/**
 * Represents a controller for and contains general functionality for playing the
 * game of Memory.
 *
 * @author Kaitlyn Fong
 */

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import memory.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button ;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;


public class MemoryController {

    @FXML
    private List<ImageView> imageList;

    @FXML
    private List<Button> buttonList;

    @FXML
    public Label statusLabel;

    @FXML
    public Label playeronescore;

    @FXML
    public Label playertwoscore;


sss
    @FXML
    private void handleCardOne() {
        cardFlipped(1);
    }
    public void cardShow1(){
        imageList.get(0).setVisible(true);
        buttonList.get(0).setVisible(false);
        }
    public void cardHide1(){
        imageList.get(0).setVisible(false);
        buttonList.get(0).setVisible(true);
    }

    @FXML
    private void handleCardTwo() {
        cardFlipped(2);
    }
    public void cardShow2(){
        imageList.get(1).setVisible(true);
        buttonList.get(1).setVisible(false);
    }
    public void cardHide2(){
        imageList.get(1).setVisible(false);
        buttonList.get(1).setVisible(true);
    }

    @FXML
    private void handleCardThree() {
        cardFlipped(3);
    }
    public void cardShow3(){
        imageList.get(2).setVisible(true);
        buttonList.get(2).setVisible(false);
    }
    public void cardHide3(){
        imageList.get(2).setVisible(false);
        buttonList.get(2).setVisible(true);
    }

    @FXML
    private void handleCardFour() {
        cardFlipped(4);
    }
    public void cardShow4(){
        imageList.get(3).setVisible(true);
        buttonList.get(3).setVisible(false);
    }
    public void cardHide4(){
        imageList.get(3).setVisible(false);
        buttonList.get(3).setVisible(true);
    }

    @FXML
    private void handleCardFive() {
        cardFlipped(5);
    }
    public void cardShow5(){
        imageList.get(4).setVisible(true);
        buttonList.get(4).setVisible(false);
    }
    public void cardHide5(){
        imageList.get(4).setVisible(false);
        buttonList.get(4).setVisible(true);
    }

    @FXML
    private void handleCardSix() {
        cardFlipped(6);
    }
    public void cardShow6(){
        imageList.get(5).setVisible(true);
        buttonList.get(5).setVisible(false);
    }
    public void cardHide6(){
        imageList.get(5).setVisible(false);
        buttonList.get(5).setVisible(true);
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
                    PauseTransition pause = new PauseTransition(
                            Duration.seconds(1)
                    );
                    pause.setOnFinished(event -> {
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

                    });
                    pause.play();
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
