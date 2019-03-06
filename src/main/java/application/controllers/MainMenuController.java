package application.controllers;

import application.utility.ViewGrabber;
import application.utility.ViewInitializer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainMenuController {
    @FXML
    private Button checkersPlayerButton;
    @FXML
    private AnchorPane mainMenuAnchorPane;

    public void loadCheckersContent() throws Exception{
        StackPane checkerboardPane = ViewInitializer.initGameboardPane(this);
        Pane contentWidgetPane = ViewGrabber.getContentWidgetPane(mainMenuAnchorPane);
        contentWidgetPane.getChildren().clear();
        contentWidgetPane.getChildren().add(checkerboardPane);
    }

    public void loadOthelloContent() {
        //Load Othello here
    }

    public void loadMemoryContent() {
        //Load Memory Here
    }

    public void loadTictactoeContent() {
        //Load Tic-Tac-Toe here
    }
}
