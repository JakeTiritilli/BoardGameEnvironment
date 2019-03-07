package othello.models;

public class Othello {
    /**
     * This class contains all the gamestate of the Othello



     */
    int turn = 0; // Player 1 or 2
    int player1Score = 0;
    int player2Score = 0;
    int[] validMoves; //array of valid moves

    boolean gameStarted = false;

}
