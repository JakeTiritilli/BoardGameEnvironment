package memory.models;

import boardgamekit.BoardGame;
import boardgamekit.players.*;
import boardgamekit.utility.InvalidMoveException;

/**
 * Represents a model for and contains general functionality for playing the
 * game of Memory.
 * 
 * @author Kaitlyn Fong
 */
public class Memory extends BoardGame{

    private Player currentPlayer;
    public int[] currentPlayerPick = new int[2];
    private Player playerOne;
    public int playerOneScore;
    private Player playerTwo;
    public int playerTwoScore;
    private int[] cardIndex = new int[7];
    public int numMatches = 0;

    public Memory(Player p1, Player p2, int d) {
       super(p1, p2, 3);

        playerOneScore = 0;
        playerTwoScore = 0;


        //SET UP VALUE OF CARDS
        cardIndex[1] = 1;
        cardIndex[2] = 2;
        cardIndex[3] = 2;
        cardIndex[4] = 1;
        cardIndex[5] = 3;
        cardIndex[6] = 3;

        playerOne = p1;
        playerTwo = p2;
        currentPlayer = playerOne;
        

        currentPlayerPick[0] = 0;
        currentPlayerPick[1] = 0;

    }

    /*
    public String getPlayerString(){
        if (currentPlayer == playerOne)
            return "One";
        else
            return "Two";
    }*/
	

    //Change the current player
    public void switchCurrentPlayer(){

        currentPlayer = (currentPlayer == playerOne)? playerTwo : playerOne;

        currentPlayerPick[0] = 0;
        currentPlayerPick[1] = 0;
    }

    //CARD MATCH
    public boolean cardMatch(){
        return (cardIndex[currentPlayerPick[0]] == cardIndex[currentPlayerPick[1]]);
            
    }

    @Override
    public void makeMove(int row, int column) {}


    public int makeMove(int cardNum) {
        //IF FIRST CARDFLIP
        if (currentPlayerPick[0] == 0 && currentPlayerPick[1] == 0){
            currentPlayerPick[0] = cardNum;
            return 1; //CASE 1: SHOW IMAGE
        }
        //IF SECOND CARDFLIP
        else {//if (currentPlayerPick[0] != 0 && currentPlayerPick[1] == 0){
            currentPlayerPick[1] = cardNum;
            if (!cardMatch()){
                //changePlayer();
                return 2;//CASE 2: SHOW IMAGE THEN FLIP BOTH CARDS BACK
            }
            else {
                //IS A CARD MATCH
                if (currentPlayer == playerOne)
                    playerOneScore ++;
                else
                    playerTwoScore ++;
                numMatches ++;
                //changePlayer();
                return 3; //CASE 3: SHOW IMAGE AND KEEP BOTH IMAGES SHOWING
            }
        }
    }
    
    public Player getCurrentPlayer()
    {
    	return currentPlayer;
    }

    public Player getPlayerOne()
    {
    	return playerOne;
    }
    public Player getPlayerTwo()
    {
    	return playerTwo;
    }
    public boolean gameIsWon(){
        return numMatches == 3;
    }

    public String whoWon() {
        if (playerOneScore > playerTwoScore)
            return "One";
        else
            return "Two";

    }
    public boolean forCurrentPlayer()
    {
    	if (currentPlayer == playerOne)
    	{
    		return (whoWon() == "One");
    			
    	}
    	else
    	{
    		return (whoWon() == "Two");
    	}
    }

}