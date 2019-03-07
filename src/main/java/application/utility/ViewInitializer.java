package application.utility;

import checkers.utility.DynamicGameboard;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ViewInitializer {
    /*
    Initializes the root Pane which will be the parent for other components.
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

    /*
    Initializes the gameboard Pane which holds the checkerboard as well as game info.
    */
    public static StackPane initGameboardPane(Object object) throws Exception {
        StackPane gameboardPane = FXMLLoader.load(object.getClass().getResource("/views/checkers/Checkerboard.fxml"));

        // You can change the size of the table based on the parameter in the
        // createDynamicGameboard() method.
        StackPane gameboard = DynamicGameboard.createDynamicGameboard(8);
        StackPane.setAlignment(gameboard, Pos.CENTER);
        gameboardPane.setStyle("-fx-background-color: lightgray");
        gameboardPane.getChildren().add(gameboard);

        return gameboardPane;
    }

    public static AnchorPane initTicTacToePane(Object object) throws Exception {
        AnchorPane tictactoPane = FXMLLoader.load(object.getClass().getResource("/views/tictactoe/TicTacToe.fxml"));
        return tictactoPane;
    }

    /*************************************************************************************************
    THIS IS JUST A METHOD STUB FOR THE CODE TO COMPILE. UPDATE ONCE MEMORY IMPLEMENTATION IS COMPLETE.
    *************************************************************************************************/
    public static Pane initMemoryPane(Object object) throws Exception {
        Pane memoryPane = FXMLLoader.load(object.getClass().getResource("/views/memory/Memory.fxml"));
        return memoryPane;
    }

    /*************************************************************************************************
    THIS IS JUST A METHOD STUB FOR THE CODE TO COMPILE. UPDATE ONCE OTHELLO IMPLEMENTATION IS COMPLETE.
    *************************************************************************************************/
    public static Pane initOthelloPane(Object object) throws Exception {
        Pane othelloPane = FXMLLoader.load(object.getClass().getResource("/views/othello/Othello.fxml"));
        return othelloPane;
    }

    /*
    Initializes the sidebar menu Pane which holds the buttons for games and functions.
    */
    private static AnchorPane initSideBarMenuPane(Object object) throws Exception {
        AnchorPane sideBarMenuPane = FXMLLoader.load(object.getClass().getResource("/views/application/SideBarMenu.fxml"));
        return sideBarMenuPane;
    }

    public static AnchorPane initMainMenuPane(Object object) throws Exception {
        AnchorPane mainMenuPane = FXMLLoader.load(object.getClass().getResource("/views/application/MainMenu.fxml"));
        return mainMenuPane;
    }
}
