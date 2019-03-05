package application;

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
        //Declaring type as Pane instead of Parent in order to access getChildren() method
        StackPane root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));

        //Adding dynamic table to the root pane
        //Can change the size of the table based on the parameter in the
        //createDynamicGameboard() method
        root.getChildren().add(DynamicGameboard.createDynamicGameboard(7));

        //Setting up stage and shows it
        primaryStage.setTitle("Board Game Environment");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}