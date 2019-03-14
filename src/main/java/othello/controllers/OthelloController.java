package othello.controllers;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class OthelloController extends Application{

    private StackPane[][] board;
    final int boardWidth = 8;
    final int boardHeight = 8;
    private final int tileSize = 60 ;

    @Override
    public void start(Stage primaryStage)
    {
        board = new StackPane[boardWidth][boardHeight];
        GridPane root = new GridPane();

        root.setHgap(2);
        root.setVgap(2);
        root.setStyle("-fx-background-color: grey;");

        for (int i = 0 ; i < boardWidth ; ++i)
        {
            for (int j = 0 ; j < boardHeight ; j++) {
                StackPane square = new StackPane();
                square.setStyle("-fx-background-color: green;");

                square.setMinWidth(tileSize);
                square.setMaxWidth(tileSize);
                square.setMinHeight(tileSize);
                square.setMaxHeight(tileSize);

                square.setStyle("-fx-background-color: green");

                board[i][j] = square;

                if((i == 3 && j == 3) || (i == 4 && j == 4))
                {
                    Circle piece = new Circle(20, Paint.valueOf("black"));
                    square.getChildren().add(piece);
                }

                if((i == 3 && j == 4) || (i == 4 && j == 3))
                {
                    Circle piece = new Circle(20, Paint.valueOf("white"));
                    square.getChildren().add(piece);
                }

                root.add(square,i, j);
            }
        }


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
