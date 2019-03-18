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

    private int currentPlayer;
    public int[] currentPlayerPick = new int[2];
    public Player playerOne;
    public int playerOneScore;
    public Player playerTwo;
    public int playerTwoScore;
    private int[] cardIndex = new int[7];
    public int numMatches = 0;

    public Memory(Player p1, Player p2, int d) {
       super(p1, p2, 3);
        

        //SET UP VALUE OF CARDS
        cardIndex[1] = 1;
        cardIndex[2] = 2;
        cardIndex[3] = 2;
        cardIndex[4] = 1;
        cardIndex[5] = 3;
        cardIndex[6] = 3;

        currentPlayer = 1;
        currentPlayerPick[0] = 0;
        currentPlayerPick[1] = 0;

    }

    public String getPlayerString(){
        if (currentPlayer == 1)
            return "One";
        else
            return "Two";
    }


    //Change the current player
    public void changePlayer(){
        if (currentPlayer == 1)
            currentPlayer = 2;
        else
            currentPlayer = 1;
        currentPlayerPick[0] = 0;
        currentPlayerPick[1] = 0;
    }

    //CARD MATCH
    public boolean cardMatch(){
        if (cardIndex[currentPlayerPick[0]] == cardIndex[currentPlayerPick[1]])
            return true;
        else
            return false;
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
                if (currentPlayer == 1)
                   playerOneScore++;
                else
                    playerTwoScore ++;
                numMatches ++;
                //changePlayer();
                return 3; //CASE 3: SHOW IMAGE AND KEEP BOTH IMAGES SHOWING
            }
        }
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

}
