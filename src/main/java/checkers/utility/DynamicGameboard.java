package checkers.utility;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.lang.reflect.Method;

/*
Author: Tyler Vu

Purpose:
This file will dynamically create a gameboard grid. This is needed because different
games may require different gameboard sizes.
 */

public class DynamicGameboard {
    /*
    Returns a StackPane container that holds the GridPane gameboard.
     */
    public static StackPane createDynamicGameboard(int rows){
        StackPane gameboardContainer = new StackPane();

        GridPane checkerboard = generateTable(rows);
        try {
            checkerboard = generateCheckerPieces(checkerboard);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Add the generated table to the container and make the container shrink to size of child table
        gameboardContainer.getChildren().add(generateTable(rows));
        gameboardContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return gameboardContainer;
    }

    /*
    Creates a table based on the number of rows. This is assuming that the length
    and width of the table are going to be equal.
     */
    private static GridPane generateTable(int rows) {
        // Styling string for cells and container
        String borderStyleString = "-fx-border-style: solid;" +
                " -fx-border-color: gray;" +
                " -fx-border-width: 3px 3px 3px 3px;";

        GridPane gameboardTable = new GridPane();
        gameboardTable.setStyle(borderStyleString);


        // Create each cell and add it to the grid pane
        for(int i = 0; i < rows; i++) { //row
            for(int j = 0; j < rows; j++) { //column
                StackPane gameboardCell = new StackPane();
                gameboardCell.setPrefSize(50,50); //Size of each cell

                //Set checker cell styling
                if ((j + i) % 2 == 0 ) {
                    gameboardCell.setStyle("-fx-background-color: red;" + borderStyleString);
                }
                else {
                    gameboardCell.setStyle("-fx-background-color: black;" + borderStyleString);
                }

                gameboardTable.add(gameboardCell, j, i); // (child, columnIndex, rowIndex)
            }
        }

        return gameboardTable;
    }

    /*
    Takes the generated checkerboard and places the appropriate checker pieces in the appropriate color cell
     */
    public static GridPane generateCheckerPieces(GridPane checkerboard) throws Exception{
        for(int i = 0; i < getNumberOfRows(checkerboard); i++) {
            for(int j = 0; j < getNumberOfColumns(checkerboard); j++) {
                StackPane cell = getComponentAtCell(i, j, checkerboard);
                if (getCellBackgroundColor(cell) == "black") {
                    placePieceInCell(i, j, cell);
                }
            }
        }
        return null;
    }

    /*
    This is a utility function that can be used to retrieve the Pane at the position row,column.
     */
    public static StackPane getComponentAtCell(int row, int column, GridPane gameboard) {
        // Makes sure that the position is valid
        try {
            checkPositionOnGridpane(row, column, gameboard);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (Node cell: gameboard.getChildren()) {
            if (GridPane.getRowIndex(cell) == row && GridPane.getColumnIndex(cell) == column) {
                return (StackPane) cell;
            }
        }
        // If for some reason the cell isn't found even after the previous checks
        return null;
    }

    /*
    Uses reflection in order to access the private method getNumberOfRows() of GridPane.
     */
    public static Integer getNumberOfRows(GridPane gridPane) throws Exception {
        Method method = gridPane.getClass().getDeclaredMethod("getNumberOfRows");
        method.setAccessible(true);
        Integer rows = (Integer) method.invoke(gridPane);
        return rows;
    }

    /*
    Uses reflection in order to access the private method getNumberOfColumns() of GridPane.
     */
    public static Integer getNumberOfColumns(GridPane gridPane) throws Exception {
        Method method = gridPane.getClass().getDeclaredMethod("getNumberOfColumns");
        method.setAccessible(true);
        Integer columns = (Integer) method.invoke(gridPane);
        return columns;
    }

    /*
    Checks to make sure that the position is a valid cell in the GridPane.
     */
    private static void checkPositionOnGridpane(int row, int column, GridPane gridPane) throws Exception{
        Integer gameboardRows = getNumberOfRows(gridPane);
        Integer gameboardColumns = getNumberOfColumns(gridPane);

        // If the row and column parameter are not in the bounds of the gameboard,
        // throw IndexOutOfBoundsException and return null
        if (row >= gameboardRows || row < 0 || column >= gameboardColumns || column < 0) {
            throw new IndexOutOfBoundsException("The position (row: " + row + ", column: " + column +
                    ") is not in the bounds of Gameboard Dimensions (rows: " +
                    gameboardRows + ", columns: " + gameboardColumns + ").");
        }
    }

    /*
    Gets the background color of the cell.
     */
    private static String getCellBackgroundColor(StackPane cell) {
        String cellStyle = cell.getStyle();
        if (!cellStyle.contains("red")) {
            return "black";
        }
        return "red";
    }

    /*
    Places a checker piece on the board with the appropriate color depending on board position.
     */
    private static void placePieceInCell(int row, int column, StackPane cell) {
        //Implentation
    }
}
