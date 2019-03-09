package application;

import application.utility.*;
import boardgamekit.players.*;
import boardgamekit.utility.*;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new RootVI().getPane();

        // Setting up stage and shows it
        primaryStage.setTitle("Board Game Environment");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        //launch(args);
        PlayerDataLoader test = new PlayerDataLoader("src/main/java/boardgamekit/players/UserData.json");
        try {
            PlayerData p1 = test.loadData("Ryan");
            p1.setScoreFor(Game.CHECKERS, 9);
            test.writeData(p1);
        } catch(Exception error) {
            throw error;
        }
    }
}
