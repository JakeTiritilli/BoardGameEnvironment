package application.utility;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ViewGrabber {

    /**
     * Returns the main content widget Pane object. This is the GUI container that holds
     * the game and main menu.
     * @param node Random JavaFx node that will be used in order to derive the scene of the JavaFx
     *             application through scene.lookup().
     * @return Pane that represents the content widget. This reference can then be used to insert a new
     * view such as a game or the main menu.
     */
    public static Pane getContentWidgetPane(Node node) {
        Scene scene = node.getScene();
        Pane contentWidgetPane = (Pane) scene.lookup("#mainContentWidget");
        return contentWidgetPane;
    }
}
