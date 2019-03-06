package application.controllers;

import application.utility.ViewGrabber;
import application.utility.ViewInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SideBarMenuController {

    @FXML
    private AnchorPane sideBarMenuAnchorPane;

    public void loadCheckersContent() throws Exception {
        StackPane checkerboardPane = ViewInitializer.initGameboardPane(this);
        Pane contentWidgetPane = ViewGrabber.getContentWidgetPane(sideBarMenuAnchorPane);
        contentWidgetPane.getChildren().clear();
        contentWidgetPane.getChildren().add(checkerboardPane);
    }

    public void loadTictactoeContent() throws Exception{
        Pane contentWidgetPane = ViewGrabber.getContentWidgetPane(sideBarMenuAnchorPane);
        contentWidgetPane.getChildren().clear();
        AnchorPane tictactoePane = ViewInitializer.initTicTacToePane(this);
        contentWidgetPane.getChildren().add(tictactoePane);
    }

    public void loadMainMenuContent() throws Exception {
        AnchorPane mainMenuPane = ViewInitializer.initMainMenuPane(this);
        Pane contentWidgetPane = ViewGrabber.getContentWidgetPane(sideBarMenuAnchorPane);
        contentWidgetPane.getChildren().clear();
        contentWidgetPane.getChildren().add(mainMenuPane);
    }

    public void closeApplication() {
        Stage stage = (Stage) sideBarMenuAnchorPane.getScene().getWindow();
        stage.close();
    }
}
