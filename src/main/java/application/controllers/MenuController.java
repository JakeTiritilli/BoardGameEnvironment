package application.controllers;

import java.util.ArrayList;

import application.utility.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

/**
 * Abstract base class to hold common functionality of
 * Menu Controllers. The {@code menuAnchorPane} property
 * must be set in a class that inherits from it before methods
 * are called. This should be done by adding an {@code initialize} method
 * to the subclass that is automatically called when the FXML is loaded.
 */
public class MenuController {
    private ArrayList<ViewInitializer> gameInitializers = new ArrayList<>();
    private ArrayList<Button> gameButtons = new ArrayList<>();

    @FXML
    Button checkersPlayButton;

    @FXML
    Button othelloPlayButton;

    @FXML
    Button ticTacToePlayButton;

    @FXML
    Button memoryPlayButton;

    @FXML
    protected void initialize() {
        gameInitializers.add(new CheckersVI());
        gameButtons.add(checkersPlayButton);
        gameInitializers.add(new OthelloVI());
        gameButtons.add(othelloPlayButton);
        gameInitializers.add(new TicTacToeVI());
        gameButtons.add(ticTacToePlayButton);
        gameInitializers.add(new MemoryVI());
        gameButtons.add(memoryPlayButton);
    }

    public void loadGameScene(ActionEvent actionEvent) throws Exception {
        Button gameButton = (Button) actionEvent.getSource();
        int gameNumber = gameButtons.indexOf(gameButton);
        ViewInitializer gameInitializer = gameInitializers.get(gameNumber);
        gameInitializer.swapPaneWith(RootVI.swapOutPane);
    }

    public void loadMainMenuContent(ActionEvent actionEvent) throws Exception{
        new MainVI().swapPaneWith(RootVI.swapOutPane);
    }

    public void loadSideMenu(ActionEvent actionEvent) throws Exception {
        new SideVI().swapPaneWith(RootVI.swapOutPane);
    }

    /**
     * Called when the "Exit" button is pressed. Closes the entire application.
     */
    public void closeApplication() {
        System.exit(0);
    }
}
