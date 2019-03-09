package application.utility;

import javafx.scene.layout.Pane;

/**
 * OthelloVI
 */
public class OthelloVI extends ViewInitializer {
    final static String fxmlResource = "/views/memory/Othello.fxml";

    public OthelloVI() {
        resource = OthelloVI.fxmlResource;
    }
}
