package application.utility;

/**
 * OthelloVI
 */
public class OthelloVI extends ViewInitializer {
    final static String fxmlResource = "/views/othello/OthelloBoard.fxml";

    public static OthelloVI create() {
        return new OthelloVI();
    }

    public OthelloVI() {
        super(fxmlResource);
    }
}
