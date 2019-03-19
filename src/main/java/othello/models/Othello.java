package othello.models;


import boardgamekit.BoardGame;
import boardgamekit.players.Player;
import othello.utility.OthelloPiece;
import othello.utility.OthelloPlayer;
import othello.utility.ValidMoveFinder;

import java.util.ArrayList;

public class Othello extends BoardGame {

    private OthelloPlayer currentTurn;
    static final String game = "Othello";

    final Integer boardWidth = 8;
    final Integer boardLength= 8;

    // GameBoardKit's player object used only to update score for the Username associated

    static public OthelloPiece[][] gameboard;

    // Hard code black to go first
    public Othello(Player p1, Player p2, int row, int col)
    {
        super(p1,p2,row,col);
        currentTurn = OthelloPlayer.BLACK; // black goes first
        initializeBoard();
    }

    public void initializeBoard()
    {
        // initialize and set board to empty
        gameboard = new OthelloPiece[boardWidth][boardLength]; // -1 = White, 0 = empty, 1 = black
        for(Integer i =0; i<boardWidth; ++i)
        {
            for(Integer j = 0; j < boardLength; ++j)
            {
                gameboard[i][j] = null;
            }
        }

        // set white starting pieces
        gameboard[3][3] = new OthelloPiece(OthelloPlayer.WHITE);
        gameboard[4][4] = new OthelloPiece(OthelloPlayer.WHITE);

        // set black starting pieces
        gameboard[3][4] = new OthelloPiece(OthelloPlayer.BLACK);
        gameboard[4][3] = new OthelloPiece(OthelloPlayer.BLACK);

        player1Score = 2;
        player2Score = 2;
    }

    /**
     *
     * @param row = the row number
     * @param column = the column number
     *            1.takes in the coordinates of the board to place he pieces
     *            2.it will first check the available move for the current player
     *            if there is an available move, it will place the piece down and flip the pieces accordingly
     *            else, switch the turn right away
     */
    public void makeMove(int row, int column)
    {
        if (!endGame()) {
//            System.out.println("Making Move");
            gameboard[row][column] = new OthelloPiece(currentTurn);
//            System.out.println("Piece set");
            ArrayList<Integer[]> flips = ValidMoveFinder.getFlips(row, column, currentTurn);
            for (Integer[] flip : flips) {
                gameboard[flip[0]][flip[1]].color = currentTurn;
            }
//            System.out.println("Pieces flipped");
            updateScore();
            currentTurn = currentTurn.getOppositeColor();
        }
        else{
//            System.out.println("invalid: game has ended");
        }
    }


    public void endGameProcedure(){
        if (player1Score > player2Score){
            setWinner(game,true);
        }
        else {
            setWinner(game,false);
        }
    }
    public boolean boardIsFull()
    {
        for(Integer i = 0; i < boardWidth; ++i)
        {
            for(Integer j = 0; j < boardLength; ++j)
            {
                if(gameboard[i][j] == null)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean playerHasMoves(OthelloPlayer player) // check if player has moves
    {
        return (ValidMoveFinder.getValidMoves(player).size() > 0);
    }

    public boolean endGame() // game ends if no players have moves left or if the game board is full
    {
        if(boardIsFull()) {
//            System.out.println("GAME OVER");
            return true;
        }

        else if(playerHasMoves(OthelloPlayer.WHITE)==false && playerHasMoves(OthelloPlayer.BLACK)== false)
        {
//            System.out.println("GAME OVER");
            return true;
        }
        return false;
    }

    public boolean gameIsWon(){
        return endGame();
    }

    public static OthelloPiece[][] getBoard(){
        return gameboard;
    }

    public Player getCurrentPlayer(){
        if (currentTurn == OthelloPlayer.BLACK){
            return player1;
        }
        else{
            return player2;
        }
    }

    public OthelloPlayer getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(OthelloPlayer currentTurn) {
        this.currentTurn = currentTurn;
    }


    public void updateScore(){

        player1Score = 0;
        player2Score = 0;
        for (int i = 0; i < boardLength; i++){
            for (int j = 0; j < boardWidth; j++){
                try {
                    if (gameboard[i][j].color == OthelloPlayer.WHITE) {
                        incPlayer2Score();
                    } else if (gameboard[i][j].color == OthelloPlayer.BLACK) {
                        incPlayer1Score();
                    }
                }
                catch (NullPointerException e){

                }
            }
        }
    }
}
