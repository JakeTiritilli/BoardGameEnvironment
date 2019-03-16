package application;

import javafx.scene.Parent;
import application.controllers.ViewInitializer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = new ViewInitializer().getRootPane();

        // Setting up stage and shows it
        primaryStage.setTitle("Board Game Environment");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
