package application.utility;

import checkers.utility.DynamicGameboard;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * This class initializes the full views for each component of the system. The views will then be inserted into
 * the main content widget found in the MainWindow.fxml view.
 * @author Tyler Vu
 */

public class ViewInitializer {

    /**
     * Initializes the root Pane which will be the parent for other components. The root pane contains the
     * main content widget and the side bar menu widget. This function will initialize both of those and
     * attach them to the root pane.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return AnchorPane that represents the root of the application; containing the main content as well as
     * the sidebar menu.
     * @throws Exception May be thrown from using the getResource() method.
     */
    public static AnchorPane initRootPane(Object object) throws Exception {
        // Init Panes that will be inserted into the main window
        AnchorPane root = FXMLLoader.load(object.getClass().getResource("/views/application/MainWindow.fxml"));
        AnchorPane mainMenuPane = initMainMenuPane(object);
        AnchorPane sideBarMenuPane = initSideBarMenuPane(object);

        // Gets the mainContentWidget Pane found in MainWindow.fxml and adds the mainMenu Pane to it.
        Pane mainContentWidget = (Pane) root.lookup("#mainContentWidget");
        mainContentWidget.getChildren().add(mainMenuPane);

        // Gets the sideBarMenuWidget Pane found in MainWindow.fxml and adds the sideBarMenu Pane to it.
        Pane sideBarMenuWidget = (Pane) root.lookup("#sideBarMenuWidget");
        sideBarMenuWidget.getChildren().add(sideBarMenuPane);

        return root;
    }

    /**
     * Initializes the gameboard Pane which holds the checkerboard as well as game info. The gameboard
     * will be created using the createDynamicGameboard() method from the DynamicGameboard class. The
     * gameboard will then be inserted into the gameboard container widget on the Checkerboard view.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return AnchorPane that represents the gameboard screen. It can then be inserted into the main content widget
     * to display.
     * @throws Exception May be thrown from using the getResource() method.
     */
    public static AnchorPane initGameboardPane(Object object) throws Exception {
        AnchorPane gameboardPane = FXMLLoader.load(object.getClass().getResource("/views/checkers/Checkerboard.fxml"));

        // Create gameboard and set style
        StackPane gameboard = DynamicGameboard.createDynamicGameboard(8);
        StackPane.setAlignment(gameboard, Pos.CENTER);
        gameboardPane.setStyle("-fx-background-color: lightgray");

        // Get container that gameboard will be placed and insert gameboard.
        StackPane checkerboardContainerWidget = (StackPane) gameboardPane.getChildren().get(1);
        checkerboardContainerWidget.getChildren().add(gameboard);

        return gameboardPane;
    }

    /**
     * Initializes the Tic-Tac-Toe Pane.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return AnchorPane that represents the Tic-Tac-Toe screen. It can then be inserted into the main content
     * widget to display.
     * @throws Exception May be thrown from using the getResource() method.
     */
    public static AnchorPane initTicTacToePane(Object object) throws Exception {
        AnchorPane tictactoPane = FXMLLoader.load(object.getClass().getResource("/views/tictactoe/TicTacToe.fxml"));
        return tictactoPane;
    }

    /**
     * Initializes the Memory Pane.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return Pane that represents the Memory screen. It can then be inserted into the main content
     * widget to display.
     * @throws Exception May be thrown from using the getResource() method.
     */
    /*************************************************************************************************
    THIS IS JUST A METHOD STUB FOR THE CODE TO COMPILE. UPDATE ONCE MEMORY IMPLEMENTATION IS COMPLETE.
    *************************************************************************************************/
    public static Pane initMemoryPane(Object object) throws Exception {
        Pane memoryPane = FXMLLoader.load(object.getClass().getResource("/views/memory/Memory.fxml"));
        return memoryPane;
    }

    /**
     * Initializes the Othello Pane.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return Pane that represents the Memory screen. It can then be inserted into the main content
     * widget to display.
     * @throws Exception May be thrown from using the getResource() method.
     */
    /*************************************************************************************************
    THIS IS JUST A METHOD STUB FOR THE CODE TO COMPILE. UPDATE ONCE OTHELLO IMPLEMENTATION IS COMPLETE.
    *************************************************************************************************/
    public static Pane initOthelloPane(Object object) throws Exception {
        Pane othelloPane = FXMLLoader.load(object.getClass().getResource("/views/othello/Othello.fxml"));
        return othelloPane;
    }

    /**
     * Initializes the sidebar menu Pane which holds the buttons for the games and functions.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return Pane that represents the side bar menu. It can then be inserted into the side bar menu
     * widget to display.
     * @throws Exception May be thrown from using the getResource() method.
     */
    private static AnchorPane initSideBarMenuPane(Object object) throws Exception {
        AnchorPane sideBarMenuPane = FXMLLoader.load(object.getClass().getResource("/views/application/SideBarMenu.fxml"));
        return sideBarMenuPane;
    }

    /**
     * Initializes the main menu Pane which holds the buttons for the games and functions.
     * @param object This is used only to access the getClass() method. This object can be anything.
     * @return Pane that represents the main menu view. It can then be inserted into the main content
     * widget to display.
     * @throws Exception May be thrown from using the getResource() method.
     */
    public static AnchorPane initMainMenuPane(Object object) throws Exception {
        AnchorPane mainMenuPane = FXMLLoader.load(object.getClass().getResource("/views/application/MainMenu.fxml"));
        return mainMenuPane;
    }
}
