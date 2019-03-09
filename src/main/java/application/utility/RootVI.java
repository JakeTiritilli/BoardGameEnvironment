package application.utility;

import javafx.scene.layout.Pane;

/**
 * RootVI
 */
public class RootVI extends ViewInitializer {
    // Reference to the underlying Pane that new games are swapped into.
    public static Pane swapOutPane;
    
    final static String fxmlResource = "/views/application/MainWindow.fxml";

    public RootVI() {
        super(fxmlResource);
    }

    /**
     * Loads the root view of the application that inserts the
     * two menu Panes on top of it. It also creates a static reference
     * to the Pane that will be used as the view that is swapped out
     * to show different games.
     * @throws Exception if an FXML file was not found.
     */
    public Pane getPane() throws Exception {
        Pane root = super.getPane();
        Pane mainMenuPane = new MainVI().getPane();
        Pane sideBarMenuPane = new SideVI().getPane();

        // Gets the mainContentWidget Pane found in MainWindow.fxml and adds the mainMenu Pane to it.
        Pane mainContentWidget = (Pane) root.lookup("#mainContentWidget");
        RootVI.swapOutPane = mainContentWidget;
        mainContentWidget.getChildren().add(mainMenuPane);

        // Gets the sideBarMenuWidget Pane found in MainWindow.fxml and adds the sideBarMenu Pane to it.
        Pane sideBarMenuWidget = (Pane) root.lookup("#sideBarMenuWidget");
        sideBarMenuWidget.getChildren().add(sideBarMenuPane);

        return root;
    }
}