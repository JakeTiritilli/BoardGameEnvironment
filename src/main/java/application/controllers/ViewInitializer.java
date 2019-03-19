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
    
	final public static String MAINMENU = "/views/application/MainMenu.fxml";
	
	final static String CHECKERS = "/views/Checkers/Checkerboard.fxml";
	final static String MEMORY = "/views/memory/Memory.fxml";
	final static String OTHELLO = "/views/othello/OthelloBoard.fxml";
	final static String TICTACTOE = "/views/tictactoe/TicTacToe.fxml";
	
	final static String LOGIN = "/views/boardgamekit/PlayerLogin.fxml";
	final static String LEADERBOARD = "/views/boardgamekit/Leaderboard.fxml";

	final static String PLAYER_DATA = "src/main/resources/json/UserData.json";
	
	/* Array of view intializers for each game.
    The order must match the order of buttons
    in {@code gameButtons}. */
	protected String[] gamesURL = { CHECKERS, OTHELLO, TICTACTOE, MEMORY };

	protected String[] gameNames = { "checkers", "othello", "tictactoe", "memory" };
    
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
