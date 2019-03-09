package application.utility;

/**
 * SideVI
 */
import javafx.scene.layout.Pane;

/**
 * OthelloVI
 */
public class SideVI extends ViewInitializer {
    final static String fxmlResource = "/views/application/SideBarMenu.fxml";

    public SideVI() {
        resource = SideVI.fxmlResource;
    }
}