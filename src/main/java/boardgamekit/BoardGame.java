package boardgamekit;

import boardgamekit.players.Player;
import boardgamekit.utility.*;

/**
 * This class holds default functionality for many common board games and
 * serves as a way to handle some of the word for each board game that
 * extends it.
 * 
 * @author Jacob Tiritilli
 */
public abstract class BoardGame {

    private String rawGameType;

    private Player player1;

    private GamePiece player1GamePiece;

    private Player player2;
    
    private GamePiece player2GamePiece;

    private Player currentPlayer;

    protected GamePiece[][] gameBoard;
    
    /**
     * Instantiates a new board game with a 1D board.
     * @param player1 the first player (this player starts)
     * @param player2 the second player
     * @param size the number of cells in the 1D game board
     */
    public BoardGame(Player player1, Player player2, int size) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        gameBoard = new GamePiece[1][size];
    }

    /**
     * Instantiates a new board game with a 2D board.
     * @param player1 the first player (this player starts)
     * @param player2 the second player
     * @param rows the number of rows in the 2D game board
     * @param columns the number of columns in the 2D game board
     */
    public BoardGame(Player player1, Player player2, int rows, int columns) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        gameBoard = new GamePiece[rows][columns];
    }
    
    /**
     * Returns whether there is a piece placed in every cell of the board.
     * @return true if ever cell is occupied on the game board, else false
     */
    public boolean boardIsFull() {
        for (GamePiece[] row : gameBoard) {
            for (GamePiece slot : row) {
                if (slot == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets the game piece for player 1. This method can be called
     * multiple times throughout in a game where each player
     * has multiple pieces.
     * @param gamePiece an enum case representing a game piece
     */
    public void setPlayer1GamePiece(GamePiece gamePiece) {
        player1GamePiece = gamePiece;
    }

    /**
     * Sets the game piece for player 1. This method can be called
     * multiple times throughout in a game where each player
     * has multiple pieces.
     * @param gamePiece an enum case representing a game piece
     */
    public void setPlayer2GamePiece(GamePiece gamePiece) {
        player2GamePiece = gamePiece;
    }

    /**
     * Returns whether the game is in winning state. This is unique
     * for each board game and must be implemented by the extending
     * class.
     * @return true if game has been won, else false.
     */
    abstract public boolean gameIsWon();

    /**
     * Sets the winner for the game and updates that player's profile.
     * @param gameType the game type of game that was being played to
     * be used in the player's profile.
     * @param forCurrentPlayer if true, will set the current player as
     * the winner. Otherwise, the other player will be set as the winner.
     */
    public void setWinner(Game gameType, boolean forCurrentPlayer) {
        Player winner = (currentPlayer == player1) ? player1 : player1;
        int currentScore = winner.getScoreFor(gameType);
        
        if (gameType != null) {
            winner.setScoreFor(gameType, currentScore + 1);
        } else {
            winner.setScoreFor(rawGameType, currentScore + 1);   
        }
    }

    /**
     * Makes a move on the gameboard by placing a piece in a given cell.
     * @param row the row number
     * @param column the column number
     * @param checkOccupied true if function should throw if that cell
     * is already occupied, else false
     * @param switchPlayers true if the function should automaticallly switch
     * players at completion, else false
     * @throws InvalidMoveException if the move was out bounds or cell is already there
     * @throws GameOverException if move was attempted to be made after the game was already over.
     */
    public void makeMove(int row, int column, boolean checkOccupied, boolean switchPlayers) throws InvalidMoveException, GameOverException {
        if (gameIsWon()) {
            throw new GameOverException("Attempts to make move after game completed.");
        }

        if (row >= gameBoard.length || column >= gameBoard[0].length) {
            throw new InvalidMoveException("Attempted to place a move out of bounds.");
        }

        if (checkOccupied && gameBoard[row][column] != null) {
            throw new InvalidMoveException("Attempted to place a move ");
        }

        gameBoard[row][column] = (currentPlayer == player1) ? player1GamePiece : player2GamePiece;
        
        if (switchPlayers) {
            switchCurrentPlayer();
        }
    }

    /**
     * Makes a move on the game board if the board is being treated an a 1D board.
     */
    public void makeMove(int position, boolean checkOccupied, boolean switchPlayers) throws InvalidMoveException, GameOverException {
        makeMove(0, position, checkOccupied, switchPlayers);
    }

    /**
     * Returns a piece at a given location on a 2D game board.
     * @param row the row numberon the game board
     * @param column the column number on the game board
     * @throws GameBoardException if the indices were out of bounds
     */
    public GamePiece returnPieceAt(int row, int column) throws GameBoardException {
        if (row >= gameBoard.length || column >= gameBoard[0].length) {
            throw new GameBoardException("Attempted to place a move out of bounds.");
        }

        return gameBoard[row][column];
    }

    /**
     * Returns a given piece on the board if the board is being treated as a 1D board.
     */
    public GamePiece returnPieceAt(int position) throws GameBoardException {
        return returnPieceAt(0, position);
    }

    /**
     * Returns the game piece of the current player.
     * @return the enum case representing the current player's piece.
     */
    public GamePiece getCurrentPlayerPiece() {
        return (currentPlayer == player1) ? player1GamePiece : player2GamePiece;
    }

    /**
     * Switched the current player to the other player.
     */
    public void switchCurrentPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}
