package application.utility;

import javafx.scene.layout.Pane;

/**
 * RootVI
 */
public class RootVI extends ViewInitializer {
    final static String fxmlResource = "/views/application/MainWindow.fxml";
    public static Pane swapOutPane;

    public RootVI() {
        resource = RootVI.fxmlResource;
    }

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