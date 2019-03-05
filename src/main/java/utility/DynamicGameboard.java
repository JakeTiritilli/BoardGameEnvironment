package utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/*
Purpose:
This file will dynamically create a gameboard grid. This is needed because different
games may require different gameboard sizes.
 */

public class DynamicGameboard {
    public static StackPane createDynamicGameboard(int rows) {
        StackPane gameboardContainer = new StackPane();

        //Debugging purposes
//        String gameboardBorderStyle = "-fx-background-color: red;";
//        gameboardContainer.setStyle(gameboardBorderStyle);

        //Add the generated table to the container and make the container shrink to size of child table
        gameboardContainer.getChildren().add(generateTable(rows));
        gameboardContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return gameboardContainer;
    }

    /*
    Creates a table based on the number of rows. This is assuming that the length
    and width of the table are going to be equal.
     */
    public static GridPane generateTable(int rows) {
        //Styling string for cells and container
        String borderStyleString = "-fx-border-style: solid;" +
                " -fx-border-color: black;" +
                " -fx-border-width: 1px 1px 1px 1px;";

        GridPane gameboardTable = new GridPane();
        gameboardTable.setStyle(borderStyleString);


        //Create each cell and add it to the grid pane
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < rows; j++) {
                Pane gameboardCell = new Pane();
                gameboardCell.setPrefSize(50,50); //Size of each cell
                gameboardCell.setStyle(borderStyleString);
                gameboardTable.add(gameboardCell, j, i); // (child, columnIndex, rowIndex)
            }
        }

        return gameboardTable;
    }
}
