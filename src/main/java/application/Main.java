package application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import utility.DynamicGameboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = initRootPane();

        // Setting up stage and shows it
        primaryStage.setTitle("Board Game Environment");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    /*
    Initializes the root Pane which will be the parent for other components.
     */
    private AnchorPane initRootPane() throws Exception {
        // Init root Pane and gameboard Pane.
        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        StackPane gameboardPane = initGameboardPane();
        AnchorPane menuPane = initMenuPane();

        // Gets the gameboardWidget Pane found in Main.fxml and adds the gameboardPane to it.
        Pane gameboardWidget = (Pane) root.lookup("#gameboardWidget");
        gameboardWidget.getChildren().add(gameboardPane);

        // Gets the gameMenuWidget Pane found in Main.fxml and adds the menu Pane to it.
        Pane gameMenuWidget = (Pane) root.lookup("#gameMenuWidget");
        gameMenuWidget.getChildren().add(menuPane);

        return root;
    }

    /*
    Initializes the gameboard Pane which holds the checkerboard as well as game info.
     */
    private StackPane initGameboardPane() throws Exception {
        StackPane gameboardPane = FXMLLoader.load(getClass().getResource("/views/Gameboard.fxml"));

        // You can change the size of the table based on the parameter in the
        // createDynamicGameboard() method.
        StackPane gameboard = DynamicGameboard.createDynamicGameboard(8);
        StackPane.setAlignment(gameboard, Pos.CENTER);
        gameboardPane.setStyle("-fx-background-color: lightgray");
        gameboardPane.getChildren().add(gameboard);

        return gameboardPane;
    }

    private AnchorPane initMenuPane() throws Exception {
        AnchorPane menuPane = FXMLLoader.load(getClass().getResource("/views/GameMenu.fxml"));
        return menuPane;
    }


    public static void main(String[] args) {
        launch(args);
    }
}