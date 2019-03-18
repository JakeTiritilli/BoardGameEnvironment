package memory.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

/**
 * Represents a controller for and contains general functionality for playing the
 * game of Memory.
 *
 * @author Kaitlyn Fong
 */
import javafx.scene.control.*;
import boardgamekit.BoardGameController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import memory.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button ;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MemoryController extends BoardGameController {
	String[] imageArray = {"src/main/resources/images/bug.png", "src/main/resources/images/bee.png",
	"src/main/resources/images/butterfly.png"};

	@FXML
	public Button reset;
	@FXML
    public Button test;

    @FXML
    private Label player1name;
    @FXML
    private Label player2name;

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
    private List<Button> buttonList;
    
    
//    @FXML
//    private void handleCardOne() {
//        cardFlipped(1);
//    }
    public void cardShow1(){
        iv1.setVisible(true);
        button1.setVisible(false);
        }
    public void cardHide1(){
        iv1.setVisible(false);
        button1.setVisible(true);
    }

//    @FXML
//    private void handleCardTwo() {
//        cardFlipped(2);
//    }
    public void cardShow2(){
        iv2.setVisible(true);
        button2.setVisible(false);
    }
    public void cardHide2(){
        iv2.setVisible(false);
        button2.setVisible(true);
    }

//    @FXML
//    private void handleCardThree() {
//        cardFlipped(3);
//    }
    public void cardShow3(){
        iv3.setVisible(true);
        button3.setVisible(false);
    }
    public void cardHide3(){
        iv3.setVisible(false);
        button3.setVisible(true);
    }
//
//    @FXML
//    private void handleCardFour() {
//        cardFlipped(4);
//    }
    public void cardShow4(){
        iv4.setVisible(true);
        button4.setVisible(false);
    }
    public void cardHide4(){
        iv4.setVisible(false);
        button4.setVisible(true);
    }

//    @FXML
//    private void handleCardFive() {
//        cardFlipped(5);
//    }
    public void cardShow5(){
        iv5.setVisible(true);
        button5.setVisible(false);
    }
    public void cardHide5(){
        iv5.setVisible(false);
        button5.setVisible(true);
    }

//    @FXML
//    private void handleCardSix() {
//        cardFlipped(6);
//    }
    public void cardShow6(){
        iv6.setVisible(true);
        button6.setVisible(false);
    }
    public void cardHide6(){
        iv6.setVisible(false);
        button6.setVisible(true);
    }

    

    private Memory game;

    @FXML
    public void initialize() {
        for (int i = 0; i < buttonList.size(); i++) {
            final int buttonNum = (i+1);
            final Button button = buttonList.get(i);
            buttonList.get(i).setOnMouseClicked(event -> cardFlipped(buttonNum));
        }
    }

    
    public void initializeGameModel() {
    	
        game = new Memory(player1,player2,3);
        player1name.setText("Player 1: " + game.getPlayerOne().getUsername());
        player2name.setText("Player 2: " + game.getPlayerTwo().getUsername());
    }

    public void startTurn() {
        statusLabel.setText("Turn: Player " + game.getCurrentPlayer().getUsername());
        player1Score.setText("Score: " + game.playerOneScore);
        player2Score.setText("Score: " + game.playerTwoScore);

    }


    public void cardFlipped(int cardNum) {

        int moveNum;

        if (!game.gameIsWon()) {
        	startTurn();
            //GAME UNFINISHED
            moveNum = game.makeMove(cardNum);
            switch(moveNum){
                case 1: //CASE 1: SHOW IMAGE AND KEEP SHOWN
                    switch(cardNum){
                        case 1: cardShow1(); break;
                        case 2: cardShow2(); break;
                        case 3: cardShow3(); break;
                        case 4: cardShow4(); break;
                        case 5: cardShow5(); break;
                        case 6: cardShow6(); break;
                    } break;
                case 2: //CASE 2: SHOW IMAGE FOR A SMALL AMOUNT OF TIME THEN FLIP BOTH CARDS BACK
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
                        game.switchCurrentPlayer();

                    });
                    pause.play();
                    break;
                case 3: //CASE 3: KEEP BOTH IMAGES SHOWING
                    player1Score.setText("Score: " + game.playerOneScore);
                    player2Score.setText("Score: " + game.playerTwoScore);
                    switch(cardNum){
                        case 1: cardShow1(); break;
                        case 2: cardShow2(); break;
                        case 3: cardShow3(); break;
                        case 4: cardShow4(); break;
                        case 5: cardShow5(); break;
                        case 6: cardShow6(); break;
                    }
                    if (game.gameIsWon()) {
                        statusLabel.setText("Game Over: Player " + game.whoWon() + " wins!");
                        game.setWinner("memory", game.forCurrentPlayer());
                        return;
                    }
                    else
                        game.switchCurrentPlayer();
                    break;

            }
            
        }
         else {
        //GAME IS FINISHED
        //statusLabel.setText("Game Over: Player " + game.whoWon() + " wins!");
        return;
         }
         }
    
   public List<Image> shuffleCards()
  {
  	List<Image> imageList = new ArrayList<>();
    	for (int i = 0; i < imageArray.length; i++)
   	{
   		File file = new File(imageArray[i]);
   		Image image = new Image(file.toURI().toString());
   		imageList.add(image);
   		imageList.add(image);
   	}
   	//Collections.shuffle(imageList);
   	return imageList;
    }
   @FXML
   public void shuffle()
   {
   	List<Image> imageList = shuffleCards();
   	iv1.setImage(imageList.get(0));
	iv2.setImage(imageList.get(2));
	iv3.setImage(imageList.get(3));
    iv4.setImage(imageList.get(1));
    iv5.setImage(imageList.get(4));
    iv6.setImage(imageList.get(5));
    button1.setVisible(true);
    button2.setVisible(true);
    button3.setVisible(true);
    button4.setVisible(true);
    button5.setVisible(true);
	button6.setVisible(true);
   }
   @FXML
   public void startNewGame(ActionEvent event)
   {

   	game = new Memory(player1, player2, 3);
  	shuffle();
    statusLabel.setText("New Game");
      player1Score.setText("0");
      player2Score.setText("0");

  }


  



}
