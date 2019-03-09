package application.utility;

/**
 * TicTacToe
 */
import javafx.scene.layout.Pane;

/**
 * OthelloVI
 */
public class TicTacToeVI extends ViewInitializer {
    final static String fxmlResource = "/views/tictactoe/TicTacToe.fxml";

    public TicTacToeVI() {
        resource = TicTacToeVI.fxmlResource;
    }
}