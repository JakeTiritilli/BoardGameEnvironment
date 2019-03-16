package boardgamekit.players;

import javafx.scene.*;

import java.io.IOException;

import boardgamekit.BoardGameController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * PlayerManager
 */
public class PlayerManager {

    private final String gameViewPath;

    private final String loginViewPath;
    
    private Scene baseScene;
    
    private Player player1;

    private Player player2;

    public PlayerManager(String gameViewPath, String loginViewPath) {
        this.gameViewPath = gameViewPath;
        this.loginViewPath = loginViewPath;
    }

    public void setPlayers(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;
    }

    public void loadGame() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gameViewPath));
            Parent gamePane = fxmlLoader.load();
            // BoardGameController gameController = fxmlLoader.getController();
            // gameController.setPlayers(player1, player2);
            // gameController.initializeGameModel();
            
            if (baseScene == null) {
                createNewStage(gamePane, 800, 690, "Board Game");
            } else {
                baseScene.setRoot(gamePane);
            }

        } catch (IOException error) {
            System.out.println("Error occurred loading the game scene.");
            System.out.println(error);
            error.printStackTrace();
        }
    }

    private void createNewStage(Parent pane, int width, int height, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        Scene newScene = new Scene(pane, width, height);
        stage.setScene(newScene);
        baseScene = newScene;
        stage.show();
    }

    public void loadLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(loginViewPath));
            Parent loginPane = fxmlLoader.load();
            PlayerLoginController loginController = fxmlLoader.getController();
            createNewStage(loginPane, 800, 690, "Login Page");
            loginController.initGameController(this);
        } catch (IOException error) {
            System.out.println("Error occurred loading the login scene.");
            System.out.println(error);
            error.printStackTrace();
        }
    }
}
