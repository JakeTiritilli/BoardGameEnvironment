package application.utility;

/**
 * TicTacToe
 */
public class TicTacToeVI extends ViewInitializer {
    final static String fxmlResource = "/views/tictactoe/TicTacToe.fxml";

    public static TicTacToeVI create() {
        return new TicTacToeVI();
    }

    public TicTacToeVI() {
        super(fxmlResource);
    }
}