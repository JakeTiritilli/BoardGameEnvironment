package othello.models;


import othello.utility.ValidMoveFinder;

import java.util.ArrayList;

public class Othello {


    private Integer currentPlayer;

    private static Integer turn;
    Integer playerScore;
    Integer[] validMoves;
    final Integer boardWidth = 8;
    final Integer boardLength= 8;

    static Integer[][] gameboard;



    // Hard code black to go first
    public Othello()
    {
        currentPlayer = 1; // black goes first
        initializeBoard();
    }

    public void initializeBoard()
    {
        // initialize and set board to empty
        gameboard = new Integer[boardWidth][boardLength]; // -1 = White, 0 = empty, 1 = black
        for(Integer i =0; i<boardWidth; ++i)
        {
            for(Integer j = 0; j < boardLength; ++j)
            {
                gameboard[i][j] = 0;
            }
        }

        // set white starting pieces
        gameboard[3][3] = -1;
        gameboard[4][4] = -1;

        // set black starting pieces
        gameboard[3][4] = 1;
        gameboard[4][3] = 1;
    }


    public void makeMove(Integer row, Integer col)
    {
        if (!endGame()) {
            ArrayList<Integer[]> moves = ValidMoveFinder.getValidMoves(turn);
            if (moves.size() > 0) {
                gameboard[row][col] = getTurn();
                ArrayList<Integer[]> flips = ValidMoveFinder.getFlips(row, col);
                for (Integer[] flip : flips) {
                    gameboard[flip[0]][flip[1]] = turn;
                }
            }
            turn = -turn;
        }
    }

    public boolean boardIsFull()
    {
        for(Integer i = 0; i < boardWidth; ++i)
        {
            for(Integer j = 0; i < boardLength; ++j)
            {
                if(gameboard[i][j] == 0)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean playerHasMoves(Integer player) // check if player has moves, if not, switch to other player
    {
        return (ValidMoveFinder.getValidMoves(player).size() > 0);
    }

    public boolean validMove() // check if selected space is valid
    {
        ArrayList<Integer[]> availableMoves = ValidMoveFinder.getValidMoves(getTurn());
        return false; // need to fill
    }

    public void checkFlips()
    {

    }

    public boolean endGame() // game ends if no players have moves left or if the game board is full
    {

        if(boardIsFull() && playerHasMoves(-1)==false && playerHasMoves(1)== false)
            return true;

        return false;
    }

    public static Integer[][] getBoard(){
        return gameboard;
    }

    public static Integer getTurn(){
        return turn;
    }

}
