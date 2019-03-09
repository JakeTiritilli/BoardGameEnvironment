package application.utility;

import application.Main;
import javafx.scene.layout.Pane;

/**
 * MainVI
 */
public class MainVI extends ViewInitializer {
    final static String fxmlResource = "/views/application/MainMenu.fxml";

    public MainVI() {
        resource = MainVI.fxmlResource;
    }
}
