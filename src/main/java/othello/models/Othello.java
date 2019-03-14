package othello.models;


import othello.utility.OthelloPiece;
import othello.utility.OthelloPlayer;
import othello.utility.ValidMoveFinder;

import java.util.ArrayList;

public class Othello {


    private OthelloPlayer currentPlayer;

    private static Integer turn;
    final Integer boardWidth = 8;
    final Integer boardLength= 8;

    static OthelloPiece[][] gameboard;

    // Hard code black to go first
    public Othello()
    {
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
    }

    /**
     *
     * @param row = the row number
     * @param col = the column number
     *            1.takes in the coordinates of the board to place he pieces
     *            2.it will first check the available move for the current player
     *            if there is an available move, it will place the piece down and flip the pieces accordingly
     *            else, switch the turn right away
     */
    public void makeMove(Integer row, Integer col)
    {
        if (!endGame()) {
            ArrayList<Integer[]> moves = ValidMoveFinder.getValidMoves(currentPlayer);
            if (moves.size() > 0) {
                gameboard[row][col] = new OthelloPiece(currentPlayer);
                ArrayList<Integer[]> flips = ValidMoveFinder.getFlips(row, col, currentPlayer);
                for (Integer[] flip : flips) {
                    gameboard[flip[0]][flip[1]].color = currentPlayer;
                }
            }
            currentPlayer = currentPlayer.getOppositeColor();
        }
    }

    public boolean boardIsFull()
    {
        for(Integer i = 0; i < boardWidth; ++i)
        {
            for(Integer j = 0; i < boardLength; ++j)
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

    public boolean validMove() // check if selected space is valid
    {
        ArrayList<Integer[]> availableMoves = ValidMoveFinder.getValidMoves(getTurn());
        return false; // need to fill
    }


    public boolean endGame() // game ends if no players have moves left or if the game board is full
    {
        if(boardIsFull() && playerHasMoves(OthelloPlayer.WHITE)==false && playerHasMoves(OthelloPlayer.BLACK)== false)
            return true;
        return false;
    }


    public static OthelloPiece[][] getBoard(){
        return gameboard;
    }

    public OthelloPlayer getTurn(){
        return currentPlayer;
    }

}
