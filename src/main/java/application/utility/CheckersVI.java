package application.utility;

import javafx.scene.layout.Pane;

/**
 * CheckersVI
 */
public class CheckersVI extends ViewInitializer {
    final static String fxmlResource = "/views/Checkers/Checkerboard.fxml";

    public CheckersVI() {
        resource = CheckersVI.fxmlResource;
    }
}