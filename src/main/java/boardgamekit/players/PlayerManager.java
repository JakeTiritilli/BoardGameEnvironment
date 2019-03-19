package boardgamekit.players;

import javafx.event.EventHandler;
import javafx.scene.*;

import java.io.IOException;

import boardgamekit.BoardGameController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Provides the functionality to make
 * the segue between displaying the player login
 * screen and the actual game that the user is trying
 * to play.
 * 
 * @author Jacob Tiritilli 
 */
public class PlayerManager {

    // File path to the FXML file of the game screen
    private final String gameViewPath;
    
    // File path to the FXML file of the login screen
    private final String loginViewPath;

    private final String leaderboardViewPath;

    private final String gameName;

    private PlayerLoader playerLoader;
    
    // Used to store the scene for reuse
    // once it has been created by the login
    // page.
    private Scene baseScene;
    
    private Player player1;

    private Player player2;

    /**
     * Creates a new instance of the class and initializes the paths
     * to both the game and login screen FXML files.
     * @param gameViewPath file path to the FXML file of the game screen
     * @param loginViewPath file path to the FXML file of the login screen
     */
    public PlayerManager(String gameViewPath, String loginViewPath, String leaderboardViewPath,
                         String jsonPath, String gameName) {

        this.gameViewPath = gameViewPath;
        this.loginViewPath = loginViewPath;
        this.leaderboardViewPath = leaderboardViewPath;
        this.gameName = gameName;

        try {
            playerLoader = new PlayerLoader(jsonPath);
        } catch (IOException error) {
            System.out.println("Error occurred loading the game scene.");
            System.out.println(error);
            error.printStackTrace();
        }
    }

    /**
     * Initializes the two players of the game.
     * @param p1 the first player
     * @param p2 the second player
     */
    public void setPlayers(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;
    }

    public String getGameName() {
        return gameName;
    }

    public PlayerLoader getPlayerLoader() {
        return playerLoader;
    }

    /**
     * Loads the game view into {@code baseScene} if available.
     * Otherwise, it creates a new window and loads the game view
     * into that window after parsing the game view's FXML file.
     * It also gets the controller of the game, and sets the players
     * and calls a method to initialize the model of the game in the
     * controller.
     */
    public void loadGame() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gameViewPath));
            Parent gamePane = fxmlLoader.load();
            
            Object gameController = fxmlLoader.getController();
            
            if (gameController instanceof BoardGameController) {
                BoardGameController boardGameController = (BoardGameController) gameController;
                boardGameController.setPlayers(player1, player2);
                boardGameController.initializeGameModel();
            }

            // Replace the login view with the game view
            baseScene.setRoot(gamePane);

        } catch (IOException error) {
            System.out.println("Error occurred loading the game scene.");
            System.out.println(error);
            error.printStackTrace();
        }
    }

    /**
     * Creates a new JavaFX stage and displays it.
     * @param pane the pane of the parent
     * @param width the width of the scene
     * @param height the height of the scene
     * @param title the title to be placed at the top of the window bar
     */
    public void createNewStage(Parent pane, int width, int height, String title, Boolean setAsBase) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        Scene newScene = new Scene(pane, width, height);
        stage.setScene(newScene);

        // If replacing the base view (login view), then change setAsBase to true.
        // (e.g. Changing login view to game view)
        if (setAsBase) {
            baseScene = newScene;
        }

        stage.show();
    }

    /**
     * Loads the login screen in a new window after parsing the FXML file.
     * Also calls a method to intialize the login controller and pass in
     * a reference it the {@code PlayerManager}.
     */
    public void loadLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(loginViewPath));
            Parent loginPane = fxmlLoader.load();
            PlayerLoginController loginController = fxmlLoader.getController();
            createNewStage(loginPane, 800, 690, "Board Game Environment", true);
            loginController.initGameController(this);
        } catch (IOException error) {
            System.out.println("Error occurred loading the login scene.");
            System.out.println(error);
            error.printStackTrace();
        }
    }

    /**
     * Loads the leaderboard table in a new window.
     */
    public void loadLeaderboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(leaderboardViewPath));
            Parent leaderboardPane = fxmlLoader.load();
            LeaderboardController loginController = fxmlLoader.getController();
            createNewStage(leaderboardPane, 600, 600, "Board Game Environment", false);
            loginController.initLeaderboardController(this);
        } catch (IOException error) {
            System.out.println("Error occurred loading the login scene.");
            System.out.println(error);
            error.printStackTrace();
        }
    }
}
