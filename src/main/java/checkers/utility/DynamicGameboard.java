package checkers.utility;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.lang.reflect.Method;

/**
 * DEPRECATED NOT USING THIS ANYMORE
 *
 * The purpose of this class is to generate a dynamic gameboard based on an int input.
 * The checkered gameboard will come back encapsulated in a StackPane.
 *
 * @author Tyler Vu
 */

public class DynamicGameboard {

    /**
     * Creates a dynamic gameboard with a variable dimension size that can be inserted into a view.
     * @param rows this will define the dimensions of the dynamically generated board. (e.g. rows = 5 = 5x5 board)
     * @return StackPane this StackPane JavaFx object will encapsulate the checkered gameboard. It can then be
     * inserted into a view.
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

    /**
     * Generates the checkered gameboard in the form of a GridPane. Each Cell within the GridPane
     * is a StackPane.
     * @param rows the dimension of the gameboard (e.g. rows = 5 = 5x5 board).
     * @return a GridPane object that will be the checkered gameboard instance.
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

    /**
     * Takes the generated checkerboard and places the appropriate checker pieces in the appropriate
     * color cell.
     * @param checkerboard a GridPane that represents the checkerboard.
     * @return A GridPane with checker game pieces placed in each appropriate cell.
     * @throws Exception This exception comes from the getNumberOfRows() and getNumberOfColumns() method.
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
        return checkerboard;
    }

    /**
     * Retrieves the StackPane which represents a cell at the position (row, column) on the gameboard.
     * @param row row of the target cell
     * @param column column of the target cell
     * @param gameboard the GridPane instance that represents the checkerboard
     * @return A StackPane that represents the Cell at the specified location
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

    /**
     * Returns an Integer that represents the number of rows within the checkerboard. Reflection
     * is used in order to access the private method getNumberOfRows() within the GridPane class.
     * @param gridPane Represents the checkerboard GridPane instance.
     * @return An Integer object that represents the number of rows within the checkerboard.
     * @throws Exception May throw an exception when using Reflection to access the private method.
     */
    public static Integer getNumberOfRows(GridPane gridPane) throws Exception {
        Method method = gridPane.getClass().getDeclaredMethod("getNumberOfRows");
        method.setAccessible(true);
        Integer rows = (Integer) method.invoke(gridPane);
        return rows;
    }

    /**
     * Returns an Integer that represents the number of columns within the checkerboard. Reflection
     * is used in order to access the private method getNumberOfColumns() within the GridPane class.
     * @param gridPane Represents the checkerbaord GridPane instance.
     * @return An Integer object that represents the number of rows within the checkerboard.
     * @throws Exception May throw an exception when using Reflection to access the private method.
     */
    public static Integer getNumberOfColumns(GridPane gridPane) throws Exception {
        Method method = gridPane.getClass().getDeclaredMethod("getNumberOfColumns");
        method.setAccessible(true);
        Integer columns = (Integer) method.invoke(gridPane);
        return columns;
    }

    /**
     * Checks to make sure that the position on the checkerboard is within the bounds of the dimension.
     * @param row The row of the position.
     * @param column The column of the position.
     * @param gridPane The GridPane that represents the checkerboard.
     * @throws Exception Three possible exception points. The first two are from getNumberOfRows() and
     * getNumberOfColumns(). The last exception point is if the position is out of bounds, an
     * IndexOutOfBoundsException will be thrown with an error message explaining the exception.
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

    /**
     * Returns the background color of the specified StackPane cell. This will target the CSS style
     * attribute "-fx-background-color" and return the found value.
     * @param cell The StackPane cell to be checked.
     * @return String representing the color of the cell. Possible values are "red" or "black".
     */
    private static String getCellBackgroundColor(StackPane cell) {
        String cellStyle = cell.getStyle();
        if (!cellStyle.contains("red")) {
            return "black";
        }
        return "red";
    }

    // TODO: Implement placing the correct colored checker gamepiece in the cell depending on the position.
    /**
     * Places the appropriate colored checker gamepiece on the cell specified in the parameter depending on
     * the cell's position on the board.
     * @param row Row of the specified cell.
     * @param column Column of the specified cell.
     * @param cell The StackPane that represents the cell to be modified.
     */
    private static void placePieceInCell(int row, int column, StackPane cell) {
        //Implentation
    }
}
