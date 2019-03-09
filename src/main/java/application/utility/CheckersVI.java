package application.utility;

/**
 * CheckersVI
 */
public class CheckersVI extends ViewInitializer {
    final static String fxmlResource = "/views/Checkers/Checkerboard.fxml";

    public static CheckersVI create() {
        return new CheckersVI();
    }

    public CheckersVI() {
        super(fxmlResource);
    }
}