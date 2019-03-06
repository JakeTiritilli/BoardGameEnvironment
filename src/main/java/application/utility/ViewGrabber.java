package application.utility;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ViewGrabber {

    /*
    Takes in any JavaFx node as a parameter and returns the contentWidgetPane.
     */
    public static Pane getContentWidgetPane(Node node) {
        Scene scene = node.getScene();
        Pane contentWidgetPane = (Pane) scene.lookup("#mainContentWidget");
        return contentWidgetPane;
    }
}
