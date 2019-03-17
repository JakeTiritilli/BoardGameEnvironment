package othello.models;


import boardgamekit.BoardGame;
import boardgamekit.players.Player;
import othello.utility.OthelloPiece;
import othello.utility.OthelloPlayer;
import othello.utility.ValidMoveFinder;

import java.util.ArrayList;

public class Othello extends BoardGame {

    private OthelloPlayer currentPlayer;


    final Integer boardWidth = 8;
    final Integer boardLength= 8;
    int p1Score = 0;
    int p2Score = 0;

    static public OthelloPiece[][] gameboard;

    // Hard code black to go first
    public Othello(Player p1, Player p2, int row, int col)
    {
        super(p1,p2,row,col);
        currentPlayer = OthelloPlayer.BLACK; // black goes first
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

        p1Score = 2;
        p2Score = 2;
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
            System.out.println("Making Move");
            gameboard[row][column] = new OthelloPiece(currentPlayer);
            System.out.println("Piece set");
            ArrayList<Integer[]> flips = ValidMoveFinder.getFlips(row, column, currentPlayer);
            for (Integer[] flip : flips) {
                gameboard[flip[0]][flip[1]].color = currentPlayer;
            }
            System.out.println("Pieces flipped");
            updateScore();
            currentPlayer = currentPlayer.getOppositeColor();
        }
        else{
            System.out.println("invalid: game has ended");
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

    public boolean playerHasMoves(OthelloPlayer player) // check if player has moves, if not, switch to other player
    {
        return (ValidMoveFinder.getValidMoves(player).size() > 0);
    }

    public boolean endGame() // game ends if no players have moves left or if the game board is full
    {
        if(boardIsFull()) {
            System.out.println("GAME OVER");
            return true;
        }

        else if(playerHasMoves(OthelloPlayer.WHITE)==false && playerHasMoves(OthelloPlayer.BLACK)== false)
        {
            System.out.println("GAME OVER");
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

    public OthelloPlayer getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(OthelloPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getP1Score()
    {
        return this.p1Score;
    }

    public int getP2Score()
    {
        return this.p2Score;
    }

    public void updateScore(){
        p1Score = 0;
        p2Score = 0;

        for (int i = 0; i < boardLength; i++){
            for (int j = 0; j < boardWidth; j++){
                try {
                    if (gameboard[i][j].color == OthelloPlayer.WHITE) {
                        p2Score++;
                    } else if (gameboard[i][j].color == OthelloPlayer.BLACK) {
                        p1Score++;
                    }
                }
                catch (NullPointerException e){

                }
            }
        }
    }
}
