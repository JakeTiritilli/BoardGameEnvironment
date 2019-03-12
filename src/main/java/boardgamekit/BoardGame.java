package boardgamekit;

import boardgamekit.players.Player;
import boardgamekit.utility.*;

/**
 * BoardGame
 */
public abstract class BoardGame {

    private String rawGameType;

    private Player player1;

    private GamePiece player1GamePiece;

    private Player player2;
    
    private GamePiece player2GamePiece;

    private Player currentPlayer;

    protected GamePiece[][] twoDBoard;

    protected GamePiece[] oneDBoard;
    
    /**
     * 
     */
    public BoardGame(Player player1, Player player2, int size) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        oneDBoard = new GamePiece[size];
    }

    /**
     * 
     */
    public BoardGame(Player player1, Player player2, int rows, int columns) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        twoDBoard = new GamePiece[rows][columns];
    }
    
    /**
     * 
     */
    public boolean boardIsFull() {
        if (oneDBoard != null) {
            return oneDBoardIsFull();
        }
        return twoDBoardIsFull();
    }

    /**
     * 
     */
    public void setPlayer1GamePiece(GamePiece gamePiece) {
        player1GamePiece = gamePiece;
    }

    /**
     * 
     */
    public void setPlayer2GamePiece(GamePiece gamePiece) {
        player2GamePiece = gamePiece;
    }

    /**
     * 
     */
    private boolean oneDBoardIsFull() {
        for (GamePiece slot : oneDBoard) {
            if (slot == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     */
    private boolean twoDBoardIsFull() {
        for (GamePiece[] row : twoDBoard) {
            for (GamePiece slot : row) {
                if (slot == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 
     */
    abstract public boolean gameIsWon();

    /**
     * 
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
     * 
     */
    public void makeMove(int position, boolean checkBounds, boolean checkOccupied, boolean switchPlayers) throws InvalidMoveException, GameOverException {
        if (gameIsWon()) {
            throw new GameOverException("Attempts to make move after game completed.");
        }
        
        if (oneDBoard == null) {
            throw new InvalidMoveException("Cannot make a 1D move on a 2D board.");
        }
        
        if (checkBounds && position >= oneDBoard.length) {
            throw new InvalidMoveException("Attempted to place a move out of bounds.");
        }
        
        if (checkOccupied && oneDBoard[position] != null) {
            throw new InvalidMoveException("Attempted to place a move ");
        }
        
        oneDBoard[position] = (currentPlayer == player1) ? player1GamePiece : player2GamePiece;
        
        if (switchPlayers) {
            switchCurrentPlayer();
        }
    }

    /**
     * 
     */
    public void makeMove(int row, int column, boolean checkBounds, boolean checkOccupied, boolean switchPlayers) throws InvalidMoveException, GameOverException {
        if (gameIsWon()) {
            throw new GameOverException("Attempts to make move after game completed.");
        }
        
        if (twoDBoard == null) {
            throw new InvalidMoveException("Cannot make a 2D move on a 1D board.");
        }

        if (checkBounds && (row >= twoDBoard.length || column >= twoDBoard[0].length)) {
            throw new InvalidMoveException("Attempted to place a move out of bounds.");
        }

        if (checkOccupied && twoDBoard[row][column] != null) {
            throw new InvalidMoveException("Attempted to place a move ");
        }

        twoDBoard[row][column] = (currentPlayer == player1) ? player1GamePiece : player2GamePiece;
        
        if (switchPlayers) {
            switchCurrentPlayer();
        }
    }

    /**
     * 
     */
    public GamePiece returnPieceAt(int row, int column) throws GameBoardException {
        if (twoDBoard == null) {
            throw new GameBoardException("Cannot use two indices on a 1D board.");
        }
        
        if (row >= twoDBoard.length || column >= twoDBoard[0].length) {
            throw new GameBoardException("Attempted to place a move out of bounds.");
        }

        return twoDBoard[row][column];
    }

    /**
     * 
     */
    public GamePiece returnPieceAt(int position) throws GameBoardException {
        if (oneDBoard == null) {
            throw new GameBoardException("Cannot use one indice on a 2D board.");
        }

        if (position >= oneDBoard.length) {
            throw new GameBoardException("Attempted to extract a out of bounds position.");
        }

        return oneDBoard[position];
    }

    /**
     * 
     */
    public GamePiece getCurrentPlayerPiece() {
        return (currentPlayer == player1) ? player1GamePiece : player2GamePiece;
    }

    /**
     * 
     */
    public void switchCurrentPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}
