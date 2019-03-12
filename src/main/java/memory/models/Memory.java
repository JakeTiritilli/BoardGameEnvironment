package memory.models;

/**
 * Represents a model for and contains general functionality for playing the
 * game of Memory.
 * 
 * @author Kaitlyn Fong
 */
public class Memory {

    private int currentPlayer;
    private int[] currentPlayerPick = new int[2];

    // Fixed size game board of TicTacToePlayer enums (either X or O).
    // A value of `null` means that the cell is open.
    //private TicTacToePlayer[] gameBoard = new TicTacToePlayer[9];
    private int playerOneScore;
    private int playerTwoScore;
    //private int[] gameBoard = new int[6];

    private int[] cardIndex = new int[7];
    private int numMatches = 0;



    /**
     * Constructs a new TicTacToe object and sets the
     * current player to the player provided to be the
     * starting player.
     * @param //firstPlayer represents the player that will start
     * the game (either X or O).
     */
    public Memory() {
        playerOneScore = 0;
        playerTwoScore = 0;
//        for (int i=0; i<gameBoard.length; i++){
//            gameBoard[i]=0;
//        }

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

    /**
     * Returns the current player at any time in the game state.
     * @return the player whose turn it currently is (either X or O).
     */
//    public int getCurrentPlayer() {
//        return currentPlayer;
//    }

    public String getPlayerString(){
        if (currentPlayer == 1)
            return "One";
        else
            return "Two";
    }

    //Check if still current player's turn
//    public boolean isTurnFinished(){
//        if (currentPlayerPick[0] != 0 && currentPlayerPick[1] != 0)
//            return true;
//        else
//            return false;
//    }
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
    /**
     * Makes a move on the game board at a given position.
     * @param //boardPosition the position on the board to move into (0-9)
     * @return true if the cell is valid (i.e., on the board and not occupied)
     * and the game is not over; otherwise, false.
     */
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
                changePlayer();
                return 2;//CASE 2: SHOW IMAGE THEN FLIP BOTH CARDS BACK
            }
            else {
                //IS A CARD MATCH
                if (currentPlayer == 1)
                    playerOneScore ++;
                else
                    playerTwoScore ++;
                numMatches ++;
                changePlayer();
                return 3; //CASE 3: KEEP BOTH IMAGES SHOWING
            }
        }
    }

    /**
     * Checks whether there is a winning sequence of three in a row
     * anywhere on the game board.
     * @return true if any player has three in a row horizontally,
     * vertically, or diagonally; otherwise, false.
     */
    //public boolean gameWon() {return horizontalWin() || verticalWin() || diagonalWin();}

    /**
     * Checks whether the board is completley full or not
     * (i.e., whether there are no open cells).
     * @return true if none of the cells in the game board
     * are null; otherwise, false.
     */

    public boolean allCardsFlipped(){
        return numMatches == 3;
    }

    public String whoWon() {
        if (playerOneScore > playerTwoScore)
            return "One";
        else
            return "Two";

    }

}
