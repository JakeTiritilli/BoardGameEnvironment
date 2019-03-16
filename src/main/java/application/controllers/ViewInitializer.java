package application.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Abstract base class representing a view initializer. This class contains
 * defualt functionality for loading various FXML files into JavaFX Panes.
 * 
 * @author Jacob Tiritilli
 */
public class ViewInitializer {
	
	// Reference to the underlying Pane that new games are swapped into.
    public static Pane swapOutPane ;
    
	final static String ROOTVI = "/views/application/MainWindow.fxml";
	final static String CHECKER = "/views/Checkers/Checkerboard.fxml";
	final static String MAINMENU = "/views/application/MainMenu.fxml";
	final static String MEMORY = "/views/memory/Memory.fxml";
	final static String OTHELLO = "/views/othello/OthelloBoard.fxml";
	final static String SIDEVI = "/views/application/SideBarMenu.fxml";
	final static String TICTACTOE = "/views/tictactoe/TicTacToe.fxml";
	
	final static String LOGIN = "/views/boardgamekit/PlayerLogin.fxml";
	
	/* Array of view intializers for each game.
    The order must match the order of buttons
    in {@code gameButtons}. */
	protected String[] gamesURL = { CHECKER, OTHELLO, TICTACTOE, MEMORY };

	  /**
     * Loads the root view of the application that inserts the
     * two menu Panes on top of it. It also creates a static reference
     * to the Pane that will be used as the view that is swapped out
     * to show different games.
     * @throws Exception if an FXML file was not found.
     */
     public Pane getRootPane() throws Exception {
        Pane root = getPane(ROOTVI);
        Pane mainMenuPane = getPane(MAINMENU);
        Pane sideBarMenuPane = getPane(SIDEVI);

        // Gets the mainContentWidget Pane found in MainWindow.fxml and adds the mainMenu Pane to it.
        Pane mainContentWidget = (Pane) root.lookup("#mainContentWidget");
        swapOutPane = mainContentWidget;
        mainContentWidget.getChildren().add(mainMenuPane);

        // // Gets the sideBarMenuWidget Pane found in MainWindow.fxml and adds the sideBarMenu Pane to it.
        // Pane sideBarMenuWidget = (Pane) root.lookup("#sideBarMenuWidget");
        // sideBarMenuWidget.getChildren().add(sideBarMenuPane);

        return root;
    }
    
	/**
	 * Loads a JavaFX Pane from a given FXML file
	 * @throws Exception if the file could not be loaded
	 * @return the Pane containing the view created from the FXML file
	 */
	public Pane getPane(String resource) throws Exception {
		return FXMLLoader.load(this.getClass().getResource(resource));
	}
	
	/**
	 * overload getPane above
	 * Loads a JavaFX Pane from a given FXML file
	 * this function used in MenuController in loadGameScene function
	 * @param int the index of the game in gamesURL
	 * @throws Exception if the file could not be loaded
	 * @return the Pane containing the view created from the FXML file
	 */
	public Pane getPane(int index) throws Exception {
		 return getPane(gamesURL[index]);
	}
}
