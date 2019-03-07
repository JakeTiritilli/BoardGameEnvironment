package othello.models;


public class Othello {


    private int currentPlayer;

    int turn;
    int playerScore;
    int[] validMoves;
    final int boardWidth = 8;
    final int boardLength= 8;

    int[][] gameboard;



    // Hard code black to go first
    public Othello()
    {
        currentPlayer = 1; // black goes first
        initializeBoard();
    }

    public void initializeBoard()
    {
        // initialize and set board to empty
        gameboard = new int[boardWidth][boardLength]; // -1 = White, 0 = empty, 1 = black
        for(int i =0; i<boardWidth; ++i)
        {
            for(int j = 0; j < boardLength; ++j)
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


    public void makeMove()
    {
        // need to fill
    }

    public boolean boardIsFull()
    {
        for(int i = 0; i < boardWidth; ++i)
        {
            for(int j = 0; i < boardLength; ++j)
            {
                if(gameboard[i][j] == 0)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean playerHasMoves(int player) // check if player has moves, if not, switch to other player
    {
        return false; // need to fill
    }

    public boolean validMove() // check if selected space is valid
    {
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




}
