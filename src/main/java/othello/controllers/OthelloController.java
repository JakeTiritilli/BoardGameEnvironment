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
import javafx.stage.Stage;

import othello.models.*;

public class OthelloController extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        final int size = 8 ;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                square.setStyle("-fx-background-color: green;");

                if((row == 3 && col == 3) || (row == 4 && col == 4))
                {
                    Circle piece = new Circle(20, Paint.valueOf("black"));
                    square.getChildren().add(piece);
                }
                if((row == 3 && col == 4) || (row == 4 && col == 3))
                {
                    Circle piece = new Circle(20, Paint.valueOf("white"));
                    square.getChildren().add(piece);
                }


                root.add(square, col, row);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
        for (int i = 0; i < size; i++) {

                // commented out version will have dynamic squares
//            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
//            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
            root.getColumnConstraints().add(new ColumnConstraints(50));
            root.getRowConstraints().add(new RowConstraints(50));
        }
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}