package application.utility;

import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * ViewIntializer
 */
public abstract class ViewInitializer {
    protected String resource;

    public Pane getPane() throws Exception {
        return FXMLLoader.load(this.getClass().getResource(resource));
    }

    public void swapPaneWith(Pane contentWidgetPane) throws Exception {
        Pane newPane = getPane();
        contentWidgetPane.getChildren().clear();
        contentWidgetPane.getChildren().add(newPane);
    }
}
