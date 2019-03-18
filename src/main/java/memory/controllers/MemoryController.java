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
    private Label player1name;
    @FXML
    private Label player2name;    
    

    @FXML
    private List<Button> buttonList;
    
    @FXML
    private List<ImageView> ivList;
    

    public void cardShow(int num)
    {    	
    	ivList.get(num-1).setVisible(true);
    	buttonList.get(num-1).setVisible(false);
     }
    
    public void cardHide(int num)
    {    	
    	ivList.get(num-1).setVisible(false);    		
    	buttonList.get(num-1).setVisible(true);
    }    
    

    private Memory game;

    @FXML
    public void initialize() {
        for (int i = 0; i < buttonList.size(); i++) {
            final int buttonNum = (i+1);
            final Button button = buttonList.get(i);
            buttonList.get(i).setOnMouseClicked(event -> cardFlipped(buttonNum));
           
        }
        
        for (int i = 0; i < ivList.size(); i++) {
           
            ivList.get(i).setVisible(false);
        }
    }

    
    public void initializeGameModel() {
    	
        game = new Memory(player1,player2,3);
        player1name.setText("Player 1: " + game.getPlayerOne().getUsername());
        player2name.setText("Player 2: " + game.getPlayerTwo().getUsername());
    }

    public void startTurn() {
        statusLabel.setText("Turn: Player " + game.getCurrentPlayer().getUsername());
        player1Score.setText("Score: " + game.getPlayer1Score());
        player2Score.setText("Score: " + game.getPlayer2Score());

    }


    public void cardFlipped(int cardNum) {

    	System.out.println("card number is" + cardNum);
        int moveNum;

        if (!game.gameIsWon()) 
        {

        	startTurn();
            //GAME UNFINISHED
            moveNum = game.makeMove(cardNum);
            
            switch(moveNum){
                case 1: //CASE 1: SHOW IMAGE AND KEEP SHOWN
                    cardShow(cardNum);
                    break;
                case 2: //CASE 2: SHOW IMAGE FOR A SMALL AMOUNT OF TIME THEN FLIP BOTH CARDS BACK
                   cardShow(cardNum);
                    PauseTransition pause = new PauseTransition(
                            Duration.seconds(1)
                    );
                   
                    pause.setOnFinished(event -> {                     
                     	cardHide(game.currentPlayerPick[0]);                    	
                    	cardHide(cardNum);                 	
                      game.switchCurrentPlayer();
                    });
                    pause.play();
                    break;
                case 3: //CASE 3: KEEP BOTH IMAGES SHOWING
                    player1Score.setText("Score: " + game.getPlayer1Score());
                    player2Score.setText("Score: " + game.getPlayer2Score());
                    cardShow(cardNum);
                    
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
         else    
        	 return;
         
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
   	ivList.get(0).setImage(imageList.get(0));
   	ivList.get(3).setImage(imageList.get(1));
   	ivList.get(1).setImage(imageList.get(2));
   	ivList.get(2).setImage(imageList.get(3));
   	ivList.get(4).setImage(imageList.get(4));
   	ivList.get(5).setImage(imageList.get(5));
   	
    for (int i = 0; i < buttonList.size(); i++)            
        buttonList.get(i).setVisible(true);    
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