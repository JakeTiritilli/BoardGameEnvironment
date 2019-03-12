package boardgamekit;

import boardgamekit.players.Player;
import boardgamekit.utility.*;

/**
 * BoardGame
 */
public abstract class BoardGame {

    private Game gameType;

    private String rawGameType;

    private Player player1;

    private GamePiece player1GamePiece;

    private GamePiece player2GamePiece;

    private Player player2;

    private Player currentPlayer;

    private GamePiece[][] twoDBoard;

    private GamePiece[] oneDBoard;

    /**
     * 
     */
    public BoardGame(Player player1, Player player2, Game gameType) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameType = gameType;
    }

    /**
     * 
     */
    public BoardGame(Player player1, Player player2, String rawGameType) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.rawGameType = rawGameType;
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
    abstract boolean gameIsWon();

    /**
     * 
     */
    public void setWinner(boolean forCurrentPlayer) {
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
    public void init1DBoard(int size) throws GameBoardException {
        if (twoDBoard != null) {
            throw new GameBoardException("Already Initialized a 2D board. Only one board can be used.");
        }

        oneDBoard = new GamePiece[size];
    }

    /**
     * 
     */
    public void init2DBoard(int rows, int columns) throws GameBoardException {
        if (oneDBoard != null) {
            throw new GameBoardException("Already Initialized a 1D board. Only one board can be used.");
        }

        twoDBoard = new GamePiece[rows][columns];
    }

    /**
     * 
     */
    public void makeMove1D(int position, boolean checkBounds, boolean checkOccupied, boolean switchPlayers) throws InvalidMoveException {
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
    public void makeMove2D(int row, int column, boolean checkBounds, boolean checkOccupied, boolean switchPlayers) throws InvalidMoveException {
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
    public GamePiece returnGamePiece2DAt(int row, int column) throws GameBoardException {
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
    public GamePiece returnGamePiece1DAt(int position) throws GameBoardException {
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
    public void switchCurrentPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}
