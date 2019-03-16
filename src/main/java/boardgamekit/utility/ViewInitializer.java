package boardgamekit.utility;

import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * Abstract base class representing a view initializer. This
 * class contains defualt functionality for loading various
 * FXML files into JavaFX Panes.
 * 
 * @author Jacob Tiritilli
 */

public abstract class ViewInitializer {
    protected String fxmlResource; // Path to the view's FXML file

    private FXMLLoader fxmlLoader;

    /**
     * Constructor to force classes that extend this
     * class to provide a path to the FXML file.
     * @param fxmlResource path to FXML file
     */
    public ViewInitializer(String fxmlResource) {
        this.fxmlResource = fxmlResource;
        fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlResource));
    }

    /**
     * Loades a JavaFX Pane from a given FXML file
     * @throws Exception if the file could not be loaded
     * @return the Pane containing the view
     * created from the FXML file
     */
    public Pane getPane() throws Exception {
        return fxmlLoader.load();
    }
}
