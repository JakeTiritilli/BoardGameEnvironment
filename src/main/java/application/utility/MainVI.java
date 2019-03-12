package application.utility;

/**
 * MainVI
 */
public class MainVI extends ViewInitializer {
    final static String fxmlResource = "/views/application/MainMenu.fxml";

    public static MainVI create() {
        return new MainVI();
    }

    public MainVI() {
        super(fxmlResource);
    }
}
